package clean.arch.service.round;

import clean.arch.domain.entity.Round;
import clean.arch.repository.RoundRepository;
import clean.arch.usecase.CreatingRoundUsecase;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static clean.arch.exception.RoundException.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * 회차 생성
 * v 이름과 추첨일을 받아서 생성
 * v 동일한 회차이름으로 생성을 시도할 경우 실패
 * v 동일한 추첨일로 생성을 시도할 경우 실패
 * v 과거의 날짜로 생성을 시도할 경우 실패
 * v 토요일이 아닌 날짜로 생성을 시도할 경우 실패
 */
class CreateRoundServiceTest {
    @Test
    void success() {
        // given
        var saturday = LocalDate.of(2023, 1, 14);
        var name = "SUCCESS_CASE";
        var request = new CreatingRoundUsecase.Request(name, saturday);
        var mockRepository = mock(RoundRepository.class);

        // when
        var throwable = catchThrowable(() -> {
            new CreateRoundService(mockRepository).create(request);
        });

        // then
        assertThat(throwable).isNull();
    }

    @Test
    void failDrawnAtIsPastDate() {
        // given
        var pastDate = LocalDate.of(2022, 12, 31);
        var name = "DRAWN_DATE_IS_PAST";
        var request = new CreatingRoundUsecase.Request(name, pastDate);
        var repository = mock(RoundRepository.class);

        // when
        var throwable = catchThrowable(() -> {
            new CreateRoundService(repository).create(request);
        });

        // then
        assertThat(throwable).isExactlyInstanceOf(DrawnAtPastDateException.class);
    }

    @Test
    void failSameName() {
        // given
        var saturday = LocalDate.of(2023, 1, 14);
        var name = "same";

        var request = new CreatingRoundUsecase.Request(name, saturday);
        var mockRepository = mock(RoundRepository.class);

        doReturn(Optional.of(
                new Round(
                        name,
                        LocalDate.of(2023, 1, 21),
                        0
                )
        ))
                .when(mockRepository)
                .findByName(eq("same"));

        // when
        var throwable = catchThrowable(() -> {
            new CreateRoundService(mockRepository).create(request);
        });

        // then
        assertThat(throwable).isExactlyInstanceOf(DuplicatedNameException.class);
    }

    @Test
    void failSameDrawnAt() {
        // given
        var drawnAt = LocalDate.of(2023, 1, 21);
        var name = "NEW";

        var request = new CreatingRoundUsecase.Request(name, drawnAt);
        var mockRepository = mock(RoundRepository.class);
        doReturn(
                Optional.of(
                        new Round(
                                "DIFF",
                                drawnAt,
                                0
                        )
                )
        )
                .when(mockRepository)
                .findByDrawnAt(eq(drawnAt));

        // when
        var throwable = catchThrowable(() -> new CreateRoundService(mockRepository).create(request));

        // then
        assertThat(throwable).isExactlyInstanceOf(DuplicatedDrawnAtException.class);
    }

    @Test
    void failNotSaturday() {
        // given
        var monday = LocalDate.of(2023, 1, 16);
        var name = "MONDAY";

        var request = new CreatingRoundUsecase.Request(name, monday);
        var mockRepository = mock(RoundRepository.class);

        // when
        var throwable = catchThrowable(() -> {
            new CreateRoundService(mockRepository).create(request);
        });

        // then
        assertThat(throwable).isExactlyInstanceOf(DrawnAtNotSaturdayException.class);
    }
}