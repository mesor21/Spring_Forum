package com.example.demo.service;

import com.example.demo.entity.Massage;
import com.example.demo.entity.User;
import com.example.demo.repository.MassageRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ForumService {
    @Autowired
    private MassageRepository massageRepository;
    @Autowired
    private UserRepository userRepository;
    @Async
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @Async
    public List<Massage> getAllMassage(){
        return massageRepository.findAll();
    }
    @Async
    public void deleteMassage(String id){
        massageRepository.delete(Long.valueOf(id));
    }
    @Async
    public void deleteUser(String id){
        userRepository.delete(Long.valueOf(id));
    }
    @Async
    public void saveNewMassage(String userId,String massege){
        Massage employee = new Massage(Long.parseLong("-1"),Long.parseLong(userId),massege);
        massageRepository.save(employee);
        User user = userRepository.getByID(Long.parseLong(userId));
        user.setCountMassage(user.getCountMassage()+1);
        userRepository.update(user);
    }
    @Async
    public void saveNewUser(String login,String email){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String incorporationDate = dateFormat.format(currentDate);;
        User employee = new User(Long.parseLong("-1"),login,incorporationDate,email,0);
        userRepository.save(employee);
    }
    @Async
    public Massage getMassageById(String id){
        return massageRepository.getByID(Long.valueOf(id));
    }
    @Async
    public User getUserById(String id){
        return userRepository.getByID(Long.valueOf(id));
    }
    @Async
    public User update(User user){
        User user1 = userRepository.getByID(user.getId());
        user.setCountMassage(user1.getCountMassage());
        user.setIncorporationDate(user1.getIncorporationDate());
        return userRepository.update(user);
    }

    @Async
    public String calculatePercentile() {
        List<User> sortedUsers = userRepository.findAll();
        String output = "";
        if(sortedUsers.size()!=0){
            Collections.sort(sortedUsers, (user1, user2) -> Integer.compare(user1.getCountMassage(), user2.getCountMassage()));

            List<Integer> messageCounts = new ArrayList<>();
            for (User user : sortedUsers) {
                messageCounts.add(user.getCountMassage());
            }

            double[] percentiles = {0.25, 0.5, 0.75}; // Примерные значения процентилей
            for (double percentile : percentiles) {
                int index = (int) (percentile * (messageCounts.size() - 1));
                int percentileValue = messageCounts.get(index);
                output = output + "Percentile " + percentile + ": " + percentileValue + "; ";
            }
        }
        return output;
    }
}
