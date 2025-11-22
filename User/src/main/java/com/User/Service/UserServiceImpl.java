package com.User.Service;

import org.springframework.stereotype.Service;
import com.User.Entity.User;
import com.User.Entity.UserRepository;
import com.User.exception.UserNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User addUser(User user) {
        user.setId(null); // Important: ensures new insert
        return userRepo.save(user);
    }

    @Override
    public User viewUser(Integer userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }
}
