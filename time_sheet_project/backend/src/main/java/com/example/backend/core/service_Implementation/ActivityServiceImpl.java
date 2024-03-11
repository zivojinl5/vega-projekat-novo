
package com.example.backend.core.service_Implementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.example.backend.core.core_repository.IActivityCoreRepository;
import com.example.backend.core.create_model.ActivityCreateModel;
import com.example.backend.core.model.Activity;
import com.example.backend.core.response_model.DayResponse;
import com.example.backend.core.response_model.MonthResponse;
import com.example.backend.core.search_model.ActivitySearchModel;
import com.example.backend.core.service.IActivityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ActivityServiceImpl implements IActivityService {
    @Autowired
    private final IActivityCoreRepository coreRepository;

    @Override
    public Activity getById(Long id) {
        Activity foundActivity = coreRepository.findById(id);
        return foundActivity;
    }

    @Override
    public Activity create(ActivityCreateModel createModel) {
        Activity savedActivity = coreRepository.save(createModel);
        return savedActivity;

    }

    @Override
    public Activity update(Long id, Activity model) {
        Activity updatedActivity = coreRepository.update(id, model);
        return updatedActivity;
    }

    @Override
    public void deleteById(Long id) {
        coreRepository.deleteById(id);

    }

    @Override
    public DayResponse getAllByDateAndUserId(LocalDate date, Long id) {
        DayResponse dayResponse = coreRepository.findAllByDateAndUserId(date, id);
        return dayResponse;

    }

    @Override
    public MonthResponse search(ActivitySearchModel searchModel) {
        Stream<Activity> activityStream = coreRepository.search(searchModel);
        List<DayResponse> dayResponses = calculateDayResponses(activityStream);
        MonthResponse monthResponse = new MonthResponse(dayResponses);
        return monthResponse;
    }

    public static List<DayResponse> calculateDayResponses(Stream<Activity> activityStream) {
        Map<LocalDate, List<Activity>> activitiesByDate = activityStream
                .collect(Collectors.groupingBy(Activity::getDate));

        List<DayResponse> dayResponses = new ArrayList<>();
        activitiesByDate.forEach((date, activityList) -> {
            dayResponses.add(new DayResponse(date, activityList));
        });

        return dayResponses;
    }

}
