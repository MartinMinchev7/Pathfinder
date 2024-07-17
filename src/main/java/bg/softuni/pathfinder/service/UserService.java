package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.data.UserRepository;
import bg.softuni.pathfinder.model.Level;
import bg.softuni.pathfinder.model.User;
import bg.softuni.pathfinder.service.dto.UserProfileDTO;
import bg.softuni.pathfinder.web.dto.UserLoginDTO;
import bg.softuni.pathfinder.web.dto.UserRegisterDTO;
import org.hibernate.dialect.SpannerSqlAstTranslator;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, UserHelperService userHelperService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.userHelperService = userHelperService;
    }

    public void register(UserRegisterDTO userRegisterDTO) {
        User user = this.modelMapper.map(userRegisterDTO, User.class);
        user.setPassword(this.passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setLevel(Level.BEGINNER);

        userRepository.save(user);
    }

    public UserProfileDTO getProfileData() {
        return modelMapper.map(userHelperService.getUser(), UserProfileDTO.class);
    }
}
