package com.example.demo.repository;

import com.example.demo.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class UserRepository {
    private final String filePlace = "src/main/resources/users.json";
    private Gson gson;

    private Comparator<User> idComparator = new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return o1.getId().compareTo(o2.getId());
        }
    };

    public UserRepository(Gson gson) {
        this.gson = gson;
    }
    @Async
    private List<User> loadData() {
        var list = new ArrayList<User>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePlace));
            list = gson.fromJson(bufferedReader, new TypeToken<List<User>>() {
            }.getType());
            bufferedReader.close();
            list.sort(idComparator);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Async
    private void writeData(List<User> users) {
        try {
            FileWriter fileWriter = new FileWriter(filePlace);
            gson.toJson(users, fileWriter);
            fileWriter.close();
            System.out.println("Lighting objects have been saved to " + filePlace + " file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Async
    public User getByID(Long id) {
        List<User> users = loadData();
        var buff = users.stream().filter(x -> x.getId() == Integer.parseInt(id.toString())).findFirst().get();
        return buff;
    }
    @Async
    public void delete(Long myClassId) {
        List<User> myClassList = loadData();
        myClassList.removeIf(x -> myClassId - 1 >= 0 && x.getId() == myClassId);
        writeData(myClassList);
    }
    @Async
    public void save(User users) {
        List<User> myClassList = loadData();
        if (myClassList.isEmpty()) {
            users.setId(Long.valueOf(1));
        } else {
            users.setId(Long.valueOf(myClassList.get(myClassList.size() - 1).getId() + 1));
        }
        myClassList.add(users);
        writeData(myClassList);
    }
    @Async
    public List<User> findAll() {
        List<User> myClassList = loadData();
        return myClassList;
    }
    @Async
    public User update(User users) {
        List<User> users1 = loadData();
        if (!users1.isEmpty() && users != null) {
            var id = 0;
            for (var item : users1) {
                if (item.getId() == users.getId()) {
                    break;
                }
                id = id + 1;
            }
            users1.set(id, users);
        }
        writeData(users1);
        users1 = loadData();
        return users1.stream().filter(x -> (x.getId()) == users.getId()).toList().get(0);
    }
}
