package com.github.Gymify.core.service;

import com.github.Gymify.core.workout.ScheduleWorkoutSessionStrategy;
import com.github.Gymify.core.workout.ScheduledWorkoutSessionAggregator;
import com.github.Gymify.persistence.entity.*;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.persistence.repository.WorkoutPlanRepository;
import com.github.Gymify.persistence.specification.WorkoutPlanSpecificationFactory;
import com.github.Gymify.security.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class WorkoutPlanServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private WorkoutPlanRepository workoutPlanRepository;

    @Mock
    private ScheduledWorkoutSessionService scheduledWorkoutSessionService;

    @Mock
    private ScheduledWorkoutSessionAggregator scheduledWorkoutSessionAggregator;

    @Mock
    private UserResourceListParentDelegate<WorkoutSession> workoutSessionListParentDelegate;

    @Mock
    private WorkoutSessionService workoutSessionService;

    @Mock
    private PrePersistEntityValidator<WorkoutSession> workoutSessionPrePersistEntityValidator;

    private final WorkoutPlanSpecificationFactory workoutPlanSpecificationFactory = new WorkoutPlanSpecificationFactory();
    private WorkoutPlanService workoutPlanService;
    private WorkoutPlan workoutPlan;
    private User user;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        this.user = new User("email@test.com", "XXX", List.of(UserAuthority.BASIC_USER));
        this.user.setId(1L);
        this.closeable = MockitoAnnotations.openMocks(this);
        this.workoutPlanService = new WorkoutPlanService(
                this.workoutPlanRepository,
                this.userService,
                this.workoutPlanSpecificationFactory,
                this.scheduledWorkoutSessionService,
                this.scheduledWorkoutSessionAggregator,
                this.workoutSessionListParentDelegate,
                this.workoutSessionService,
                this.workoutSessionPrePersistEntityValidator
        );

        WorkoutSession workoutSession = new WorkoutSession();
        workoutSession.setId(1L);
        workoutSession.setUser(user);
        workoutSession.setDayOfWeek(DayOfWeek.MONDAY);
        workoutSession.setDuration(Duration.ZERO);
        workoutSession.setStartTime(LocalTime.MIDNIGHT);

        this.workoutPlan = new WorkoutPlan();
        this.workoutPlan.setId(1L);
        this.workoutPlan.setUser(this.user);
        this.workoutPlan.setWorkoutSessions(
                List.of(workoutSession)
        );
        this.workoutPlan.setActive(false);
        this.workoutPlan.setName("FBW");
    }

    @AfterEach
    void closeService() throws Exception {
        this.closeable.close();
    }

    @Test
    void add_validObject_returnAdded() {
        doReturn(this.workoutPlan).when(this.workoutPlanRepository).save(this.workoutPlan);
        assertThat(this.workoutPlanService.add(this.workoutPlan)).isEqualTo(this.workoutPlan);
    }

    @Test
    void update_updateName_nameChanged() {
        WorkoutPlan workoutPlan = new WorkoutPlan();
        workoutPlan.setId(this.workoutPlan.getId());
        workoutPlan.setName("FBW v2");

        doReturn(this.user).when(this.userService).getCurrentUser();
        doReturn(Optional.ofNullable(this.workoutPlan)).when(this.workoutPlanRepository).findById(workoutPlan.getId());
        doReturn(this.workoutPlan).when(this.workoutPlanRepository).save(any());

        assertThat(this.workoutPlanService.update(workoutPlan).getName()).isEqualTo(workoutPlan.getName());
    }

    @Test
    void update_updateWorkoutSessions_collectionHasExpectedSize() {
        WorkoutPlan workoutPlan = new WorkoutPlan();
        workoutPlan.setId(this.workoutPlan.getId());
        workoutPlan.setName(this.workoutPlan.getName());
        workoutPlan.setWorkoutSessions(new ArrayList<>());

        WorkoutSession workoutSession = new WorkoutSession();
        workoutSession.setId(1L);
        workoutSession.setUser(user);
        workoutSession.setDayOfWeek(DayOfWeek.MONDAY);
        workoutSession.setDuration(Duration.ZERO);
        workoutSession.setStartTime(LocalTime.MIDNIGHT);
        workoutPlan.getWorkoutSessions().add(workoutSession);

        doReturn(this.user).when(this.userService).getCurrentUser();
        doReturn(Optional.ofNullable(this.workoutPlan)).when(this.workoutPlanRepository).findOne((Specification<WorkoutPlan>) any());
        doReturn(List.of(workoutSession)).when(this.workoutSessionListParentDelegate).updateCollection(any(), any());
        Mockito.when(this.workoutPlanRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        WorkoutPlan update = this.workoutPlanService.update(workoutPlan);
        assertThat(update.getWorkoutSessions().size()).isEqualTo(1);
    }

    @Test
    void validateEntity_planNotActive_skipValidation() {
        this.workoutPlanService.validateEntity(this.workoutPlan);
        verify(this.workoutPlanRepository, times(0)).findOne((Specification<WorkoutPlan>) any());
    }

    @Test
    void validateEntity_planActivePlanActiveExists_shouldThrowRuntimeException() {
        WorkoutPlan workoutPlan = new WorkoutPlan();
        workoutPlan.setId(this.workoutPlan.getId());
        workoutPlan.setName(this.workoutPlan.getName());
        workoutPlan.setActive(true);

        WorkoutPlan persistedWorkoutPlan = new WorkoutPlan();
        workoutPlan.setId(2L);
        workoutPlan.setName(this.workoutPlan.getName());
        workoutPlan.setActive(true);

        doReturn(this.user).when(this.userService).getCurrentUser();
        doReturn(Optional.of(persistedWorkoutPlan)).when(this.workoutPlanRepository).findOne((Specification<WorkoutPlan>) any());

        Assertions.assertThrows(RuntimeException.class, () -> this.workoutPlanService.validateEntity(workoutPlan));

        verify(this.workoutPlanRepository, times(1)).findOne((Specification<WorkoutPlan>) any());
    }

    @Test
    void validateEntity_planActivePlanActiveNotExists_shouldNotThrowRuntimeException() {
        WorkoutPlan workoutPlan = new WorkoutPlan();
        workoutPlan.setId(this.workoutPlan.getId());
        workoutPlan.setName(this.workoutPlan.getName());
        workoutPlan.setActive(true);

        doReturn(this.user).when(this.userService).getCurrentUser();
        doReturn(Optional.empty()).when(this.workoutPlanRepository).findOne((Specification<WorkoutPlan>) any());

        Assertions.assertDoesNotThrow(() -> this.workoutPlanService.validateEntity(workoutPlan));
        verify(this.workoutPlanRepository, times(1)).findOne((Specification<WorkoutPlan>) any());
    }

    @Test
    void delete_planByIdNotExists_shouldNotCallRepositoryDeleteMethod() {
        doReturn(this.user).when(this.userService).getCurrentUser();
        doReturn(Optional.empty()).when(this.workoutPlanRepository).findOne((Specification<WorkoutPlan>) any());

        this.workoutPlanService.delete(this.workoutPlan.getId());
        verify(this.workoutPlanRepository, times(0)).delete(any());
    }

    @Test
    void delete_planByIdExists_shouldCallRepositoryDeleteMethod() {
        doReturn(this.user).when(this.userService).getCurrentUser();
        doReturn(Optional.of(this.workoutPlan)).when(this.workoutPlanRepository).findOne((Specification<WorkoutPlan>) any());

        this.workoutPlanService.delete(this.workoutPlan.getId());
        verify(this.workoutPlanRepository, times(1)).delete(any());
    }

    @Test
    void activate_activateActivatedPlan_skip() {
        WorkoutPlan workoutPlan = new WorkoutPlan();
        workoutPlan.setId(this.workoutPlan.getId());
        workoutPlan.setName(this.workoutPlan.getName());
        workoutPlan.setActive(true);

        doReturn(this.user).when(this.userService).getCurrentUser();
        doReturn(Optional.of(workoutPlan)).when(this.workoutPlanRepository).findOne((Specification<WorkoutPlan>) any());

        this.workoutPlanService.activate(this.workoutPlan);
        verify(this.scheduledWorkoutSessionAggregator, times(0)).deleteScheduled();
        verify(this.scheduledWorkoutSessionAggregator, times(0)).getScheduleWorkoutSessionStrategy();
    }

    @Test
    void activate_activateNotActivatedPlan_callDeleteScheduled() {
        WorkoutPlan workoutPlan = new WorkoutPlan();
        workoutPlan.setId(this.workoutPlan.getId());
        workoutPlan.setName(this.workoutPlan.getName());
        workoutPlan.setActive(false);

        ScheduleWorkoutSessionStrategy scheduleWorkoutSessionStrategyMock = mock(ScheduleWorkoutSessionStrategy.class);

        doReturn(this.user).when(this.userService).getCurrentUser();
        doReturn(scheduleWorkoutSessionStrategyMock).when(this.scheduledWorkoutSessionAggregator).getScheduleWorkoutSessionStrategy();
        doReturn(Optional.of(workoutPlan)).when(this.workoutPlanRepository).findOne((Specification<WorkoutPlan>) any());

        this.workoutPlanService.activate(this.workoutPlan);
        verify(this.scheduledWorkoutSessionAggregator, times(1)).deleteScheduled();
    }

    @Test
    void activate_activateNotActivatedPlan_scheduleWorkoutSessions() {
        WorkoutPlan workoutPlan = new WorkoutPlan();
        workoutPlan.setId(this.workoutPlan.getId());
        workoutPlan.setName(this.workoutPlan.getName());
        workoutPlan.setActive(false);

        ScheduleWorkoutSessionStrategy scheduleWorkoutSessionStrategyMock = mock(ScheduleWorkoutSessionStrategy.class);

        doReturn(this.user).when(this.userService).getCurrentUser();
        doReturn(scheduleWorkoutSessionStrategyMock).when(this.scheduledWorkoutSessionAggregator).getScheduleWorkoutSessionStrategy();
        doReturn(Optional.of(workoutPlan)).when(this.workoutPlanRepository).findOne((Specification<WorkoutPlan>) any());

        this.workoutPlanService.activate(this.workoutPlan);
        verify(scheduleWorkoutSessionStrategyMock, times(1)).scheduleWorkoutSessions(any());
    }
}
