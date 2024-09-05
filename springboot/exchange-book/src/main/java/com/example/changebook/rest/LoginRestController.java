package com.example.changebook.rest;

import com.example.changebook.dto.AuthendicationResponse;
import com.example.changebook.dto.loginDTO.LoginCredentialsDTO;
import com.example.changebook.dto.loginDTO.LoginResponseDTO;
import com.example.changebook.dto.usersDTO.UserReadOnlyDTO;
import com.example.changebook.jwtUtil.JwtUtil;
import com.example.changebook.mapper.Mapper;
import com.example.changebook.model.User;
import com.example.changebook.service.IUserService;
import com.example.changebook.service.exceptions.EntityNotFoundException;
import com.example.changebook.service.UserDetailService.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginRestController {
    private final IUserService userService;
//    private final UserInsertValidator insertValidator;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;


    @Operation(summary = "login with credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User logged in",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Invalid password unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content)})
    @PostMapping("/login")
    public AuthendicationResponse loginUser(@RequestBody LoginCredentialsDTO dto, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {

        User user;
        LoginResponseDTO responseDTO;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
            user = userService.getUserByUsername(dto.username);
            responseDTO = Mapper.mapToLoginResponseDTO(user);
        } catch (BadCredentialsException  | UsernameNotFoundException e) {
            throw new BadCredentialsException("Incorrect username or password!");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
            return null;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails.getUsername(),responseDTO.getUserId(), String.valueOf(responseDTO.getRole()),responseDTO.getRoleEntityId());

        return new AuthendicationResponse(jwt);

    }

}
