package com.github.Gymify.core.service;

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
class SetServiceTest {

    @Mock
    private UserResourceRepository<Set> userResourceRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserResourceSpecificationFactory<Set> specificationFactory;

    private SetService setService;
    private User user;
    private Set set;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        this.closeable = MockitoAnnotations.openMocks(this);

        this.setService = new SetService(
            this.userResourceRepository,
            this.userService,
            this.specificationFactory
        );

        this.user = new User("email@test.com", "XXX", List.of(UserAuthority.BASIC_USER));
        this.user.setId(1L);

        this.set = new Set();
        this.set.setId(1L);
        this.set.setUser(user);
        this.set.setPlannedRestDuration(0);
        this.set.setRepsQuantity(2);
    }

    @AfterEach
    void tearDown() throws Exception {
        this.closeable.close();
    }

    @Test
    void add_validObject_returnAdded() {
        doReturn(this.set).when(this.userResourceRepository).save(this.set);
        assertThat(this.setService.add(this.set)).isEqualTo(this.set);
    }

    @Test
    void update_updatePlannedRestDuration_plannedRestDurationChanged() {
        Set set = this.set.deepCopy();
        set.setPlannedRestDuration(2);

        Specification mock = Mockito.mock(Specification.class);
        doReturn(mock).when(this.specificationFactory).idEquals(any());

        doReturn(this.user).when(this.userService).getCurrentUser();
        doReturn(Optional.ofNullable(this.set)).when(this.userResourceRepository).findOne((Specification<Set>) any());
        Mockito.when(this.userResourceRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        assertThat(this.setService.update(set).getPlannedRestDuration()).isEqualTo(set.getPlannedRestDuration());
    }

    @Test
    void validateEntity_valid_doesNotThrowException() {
        Assertions.assertDoesNotThrow(() -> this.setService.validateEntity(this.set));
    }

    @Test
    void validateEntity_repsQuantityNull_throwRuntimeException() {
        Set set = this.set.deepCopy();
        set.setRepsQuantity(null);

        Assertions.assertThrows(RuntimeException.class, () -> this.setService.validateEntity(set));
    }

    @Test
    void validateEntity_repsQuantityNegative_throwRuntimeException() {
        Set set = this.set.deepCopy();
        set.setRepsQuantity(-1);

        Assertions.assertThrows(RuntimeException.class, () -> this.setService.validateEntity(set));
    }
}
