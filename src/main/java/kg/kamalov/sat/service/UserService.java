package kg.kamalov.sat.service;

import kg.kamalov.sat.exception.EmailNotFoundException;
import kg.kamalov.sat.model.User;
import kg.kamalov.sat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService{
    private final UserRepository repository;

    /**
     * Сохранение пользователя
     */
    @Transactional
    public void save(User user){
        repository.save(user);
    }

    public boolean isExistsEmail(String e){
        return repository.existsByEmail(e);
    }

    /**
     * Получение пользователя по email
     *
     * @return User
     */
    public User findByEmail(String email){
        return repository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("User not found"));
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User currentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return this.findByEmail(email);
    }
}
