package com.example.demo.repository;

import com.example.demo.entity.Massage;
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
public class MassageRepository {
    private final String filePlace = "src/main/resources/massage.json";
    private Gson gson;

    private Comparator<Massage> idComparator = new Comparator<Massage>() {
        @Override
        public int compare(Massage o1, Massage o2) {
            return o1.getId().compareTo(o2.getId());
        }
    };

    public MassageRepository(Gson gson) {
        this.gson = gson;
    }
    @Async
    private List<Massage> loadData() {
        var list = new ArrayList<Massage>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePlace));
            list = gson.fromJson(bufferedReader, new TypeToken<List<Massage>>() {
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
    private void writeData(List<Massage> employee) {
        try {
            FileWriter fileWriter = new FileWriter(filePlace);
            gson.toJson(employee, fileWriter);
            fileWriter.close();
            System.out.println("Lighting objects have been saved to " + filePlace + " file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Async
    public Massage getByID(Long id) {
        List<Massage> massages = loadData();
        var buff = massages.stream().filter(x -> x.getId() == Integer.parseInt(id.toString())).findFirst().get();
        return buff;
    }
    @Async
    public void delete(Long myClassId) {
        List<Massage> myClassList = loadData();
        myClassList.removeIf(x -> myClassId - 1 >= 0 && x.getId() == myClassId);
        writeData(myClassList);
    }
    @Async
    public void save(Massage massage) {
        List<Massage> myClassList = loadData();
        if (myClassList.isEmpty()) {
            massage.setId(Long.valueOf(1));
        } else {
            massage.setId(Long.valueOf(myClassList.get(myClassList.size() - 1).getId() + 1));
        }
        myClassList.add(massage);
        writeData(myClassList);
    }
    @Async
    public List<Massage> findAll() {
        List<Massage> myClassList = loadData();
        return myClassList;
    }
    @Async
    public Massage update(Massage patient) {
        List<Massage> massages = loadData();
        if (!massages.isEmpty() && patient != null) {
            var id = 0;
            for (var item : massages) {
                if (item.getId() == patient.getId()) {
                    break;
                }
                id = id + 1;
            }
            massages.set(id, patient);
        }
        writeData(massages);
        massages = loadData();
        return massages.stream().filter(x -> (x.getId()) == patient.getId()).toList().get(0);
    }
}
