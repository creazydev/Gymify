package com.github.Gymify.core.service;

import com.github.Gymify.persistence.entity.Exercise;
import com.github.Gymify.persistence.entity.Set;
import com.github.Gymify.persistence.entity.User;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
class ExerciseServiceTest {

    @Mock
    private UserResourceRepository<Exercise> userResourceRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserResourceSpecificationFactory<Exercise> specificationFactory;

    @Mock
    private UserResourceListParentDelegate<Set> setListParentDelegate;

    private ExerciseService exerciseService;
    private User user;
    private Exercise exercise;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        this.closeable = MockitoAnnotations.openMocks(this);

        this.exerciseService = new ExerciseService(
            this.userResourceRepository,
            this.userService,
            this.specificationFactory,
            this.setListParentDelegate
        );

        this.user = new User("email@test.com", "XXX", List.of(UserAuthority.BASIC_USER));
        this.user.setId(1L);

        this.exercise = new Exercise();
        this.exercise.setId(1L);
        this.exercise.setUser(user);
        this.exercise.setPlannedRestDuration(0);
        this.exercise.setName("Test");

        Set set = new Set();
        set.setId(2L);

        this.exercise.setSets(List.of(set));
    }

    @AfterEach
    void tearDown() throws Exception {
        this.closeable.close();
    }

    @Test
    void add_validObject_returnAdded() {
        doReturn(this.exercise.getSets()).when(this.setListParentDelegate).updateCollection(any(), any());
        doReturn(this.exercise).when(this.userResourceRepository).save(this.exercise);

        assertThat(this.exerciseService.add(this.exercise)).isEqualTo(this.exercise);
    }

    @Test
    void update_updatePlannedRestDuration_plannedRestDurationChanged() {
        Exercise exercise = this.exercise.deepCopy();
        exercise.setPlannedRestDuration(2);

        Specification mock = Mockito.mock(Specification.class);
        doReturn(mock).when(this.specificationFactory).idEquals(any());

        doReturn(this.user).when(this.userService).getCurrentUser();
        doReturn(Optional.ofNullable(this.exercise)).when(this.userResourceRepository).findOne((Specification<Exercise>) any());
        Mockito.when(this.userResourceRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        assertThat(this.exerciseService.update(exercise).getPlannedRestDuration()).isEqualTo(exercise.getPlannedRestDuration());
    }

    @Test
    void update_updateSets_collectionHasExpectedSize() {
        Exercise exercise = this.exercise.deepCopy();
        exercise.setSets(List.of(new Set()));

        Specification mock = Mockito.mock(Specification.class);
        doReturn(mock).when(this.specificationFactory).idEquals(any());

        doReturn(this.user).when(this.userService).getCurrentUser();
        doReturn(Optional.ofNullable(this.exercise)).when(this.userResourceRepository).findOne((Specification<Exercise>) any());
        doReturn(exercise.getSets()).when(this.setListParentDelegate).updateCollection(any(), any());
        Mockito.when(this.userResourceRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        Exercise update = this.exerciseService.update(exercise);
        assertThat(update.getSets().size()).isEqualTo(1);
    }

    @Test
    void validateEntity_valid_doesNotThrowException() {
        Assertions.assertDoesNotThrow(() -> this.exerciseService.validateEntity(this.exercise));
    }

    @Test
    void validateEntity_setsNull_throwRuntimeException() {
        Exercise exercise = this.exercise.deepCopy();
        exercise.setSets(null);

        Assertions.assertThrows(RuntimeException.class, () -> this.exerciseService.validateEntity(exercise));
    }

    @Test
    void validateEntity_nameBlank_throwRuntimeException() {
        Exercise exercise = this.exercise.deepCopy();
        exercise.setName(null);

        Assertions.assertThrows(RuntimeException.class, () -> this.exerciseService.validateEntity(exercise));
    }
}
