package ru.javaops.topjava.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.repository.UserRepository;
import ru.javaops.topjava.repository.VoteRepository;

import java.time.LocalTime;


@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    static final LocalTime DEADLINE = LocalTime.of(11, 00, 00);

    @Transactional
    public Vote save(int userId, Vote vote) {
        vote.setUser(userRepository.getExisted(userId));
        return voteRepository.save(vote);
    }


}
