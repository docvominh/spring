package com.vominh.example.spring.mongo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vominh.example.spring.mongo.config.security.AppUserDetails;
import com.vominh.example.spring.mongo.config.security.JwtService;
import com.vominh.example.spring.mongo.data.document.UserDocument;
import com.vominh.example.spring.mongo.data.document.sequence.SequenceService;
import com.vominh.example.spring.mongo.data.mapper.UserMapper;
import com.vominh.example.spring.mongo.data.model.UserModel;
import com.vominh.example.spring.mongo.data.repository.IUserRepo;
import com.vominh.example.spring.mongo.exception.BadRequestException;
import com.vominh.example.spring.mongo.exception.UserExistedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends GenericService<UserModel, UserDocument, String> {
    private final IUserRepo repo;
    private final UserMapper mapper;
    private final SequenceService sequenceService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(IUserRepo repo, UserMapper mapper, SequenceService sequenceService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, ObjectMapper objectMapper) {
        super(repo, mapper, objectMapper);
        this.repo = repo;
        this.mapper = mapper;
        this.sequenceService = sequenceService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;

        modelType = objectMapper.getTypeFactory().constructCollectionType(List.class, UserModel.class);
    }

    public UserModel signup(UserModel user) {

        if (StringUtils.isEmpty(user.getEmail())) {
            throw new BadRequestException("Email can not be empty");
        }

        if (!user.getPassword().equals(user.getRePassword())) {
            throw new BadRequestException("Password and RePassword not matched");
        }

        var optionalUser = repo.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new UserExistedException("Email already used");
        }

        var document = mapper.modelToDocument(user);
        document.setUserId(sequenceService.getNextSequence("globalSequences"));
        document.setPassword(passwordEncoder.encode(user.getPassword()));

        document = repo.save(document);
        return mapper.documentToModel(document);
    }

    public String signIn(String user, String password) {
        // throws AuthenticationException
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user, password));

        // Create token
        var userDetails = (AppUserDetails) authentication.getPrincipal();

        return jwtService.createToken(userDetails);
    }

    public UserModel getInfo(Integer userId) {
        var optionalUser = repo.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User ID not found");
        }

        return mapper.documentToModel(optionalUser.get());
    }
}
