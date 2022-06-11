package com.github.Gymify.core.service;

import com.github.Gymify.persistence.entity.Exercise;
import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.entity.WorkoutSession;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.persistence.repository.UserResourceRepository;
import com.github.Gymify.persistence.specification.UserResourceSpecificationFactory;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
class WorkoutSessionServiceTest {

    @Mock
    private UserResourceRepository<WorkoutSession> userResourceRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserResourceSpecificationFactory<WorkoutSession> specificationFactory;

    @Mock
    private UserResourceListParentDelegate<Exercise> exerciseListParentDelegate;


    private WorkoutSessionService workoutSessionService;
    private User user;
    private WorkoutSession workoutSession;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        this.closeable = MockitoAnnotations.openMocks(this);
        this.workoutSessionService = new WorkoutSessionService(
            this.userResourceRepository,
            this.userService,
            this.specificationFactory,
            this.exerciseListParentDelegate
        );

        this.user = new User("email@test.com", "XXX", List.of(UserAuthority.BASIC_USER));
        this.user.setId(1L);

        this.workoutSession = new WorkoutSession();
        this.workoutSession.setId(1L);
        this.workoutSession.setUser(user);
        this.workoutSession.setDayOfWeek(DayOfWeek.MONDAY);
        this.workoutSession.setDuration(Duration.ZERO);
        this.workoutSession.setStartTime(LocalTime.MIDNIGHT);
        this.workoutSession.setExercises(new ArrayList<>());
    }

    @AfterEach
    void closeService() throws Exception {
        this.closeable.close();
    }

    @Test
    void add_validObject_returnAdded() {
        doReturn(this.workoutSession.getExercises()).when(this.exerciseListParentDelegate).updateCollection(any(), any());
        doReturn(this.workoutSession).when(this.userResourceRepository).save(this.workoutSession);

        assertThat(this.workoutSessionService.add(this.workoutSession)).isEqualTo(this.workoutSession);
    }

    @Test
    void update_updateDuration_durationChanged() {
        WorkoutSession workoutSession = this.workoutSession.deepCopy();
        workoutSession.setDuration(Duration.ZERO);

        Specification mock = Mockito.mock(Specification.class);
        doReturn(mock).when(this.specificationFactory).idEquals(any());

        doReturn(this.user).when(this.userService).getCurrentUser();
        doReturn(Optional.ofNullable(this.workoutSession)).when(this.userResourceRepository).findOne((Specification<WorkoutSession>) any());
        Mockito.when(this.userResourceRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        assertThat(this.workoutSessionService.update(workoutSession).getDuration()).isEqualTo(workoutSession.getDuration());
    }

    @Test
    void update_updateExercises_collectionHasExpectedSize() {
        WorkoutSession workoutSession = this.workoutSession.deepCopy();
        workoutSession.setExercises(List.of(new Exercise()));

        Specification mock = Mockito.mock(Specification.class);
        doReturn(mock).when(this.specificationFactory).idEquals(any());

        doReturn(this.user).when(this.userService).getCurrentUser();
        doReturn(Optional.ofNullable(this.workoutSession)).when(this.userResourceRepository).findOne((Specification<WorkoutSession>) any());
        doReturn(workoutSession.getExercises()).when(this.exerciseListParentDelegate).updateCollection(any(), any());
        Mockito.when(this.userResourceRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        WorkoutSession update = this.workoutSessionService.update(workoutSession);
        assertThat(update.getExercises().size()).isEqualTo(1);
    }

    @Test
    void validateEntity_valid_doesNotThrowException() {
        Assertions.assertDoesNotThrow(() -> this.workoutSessionService.validateEntity(this.workoutSession));
    }

    @Test
    void validateEntity_durationNull_throwRuntimeException() {
        WorkoutSession workoutSession = this.workoutSession.deepCopy();
        workoutSession.setDuration(null);

        Assertions.assertThrows(RuntimeException.class, () -> this.workoutSessionService.validateEntity(workoutSession));
    }

    @Test
    void validateEntity_dayOfWeekNull_throwRuntimeException() {
        WorkoutSession workoutSession = this.workoutSession.deepCopy();
        workoutSession.setDayOfWeek(null);

        Assertions.assertThrows(RuntimeException.class, () -> this.workoutSessionService.validateEntity(workoutSession));
    }

    @Test
    void validateEntity_startTimeNull_throwRuntimeException() {
        WorkoutSession workoutSession = this.workoutSession.deepCopy();
        workoutSession.setStartTime(null);

        Assertions.assertThrows(RuntimeException.class, () -> this.workoutSessionService.validateEntity(workoutSession));
    }

    @Test
    void validateEntity_exercisesNull_throwRuntimeException() {
        WorkoutSession workoutSession = this.workoutSession.deepCopy();
        workoutSession.setExercises(null);

        Assertions.assertThrows(RuntimeException.class, () -> this.workoutSessionService.validateEntity(workoutSession));
    }
}
