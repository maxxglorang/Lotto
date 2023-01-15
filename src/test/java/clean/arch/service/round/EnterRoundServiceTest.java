package clean.arch.service.round;

import clean.arch.domain.entity.Game;
import clean.arch.domain.entity.Round;
import clean.arch.exception.GameException;
import clean.arch.repository.GameRepository;
import clean.arch.repository.RoundRepository;
import clean.arch.usecase.CreatingRoundUsecase;
import clean.arch.usecase.EnteringRoundUsecase;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.Optional;

import static clean.arch.exception.RoundException.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * 사용자 응모
 * v 회차이름과 돈을 받아서 응모 생성
 * v 없는 회차이름으로 응모 생성시 실패
 * v 최소 한게임 1000원 보다 돈이 적을 경우 실패
 */
class EnterRoundServiceTest {
    @Test
    void enter() {
        // given
        var day = LocalDate.of(2023, 1, 21);
        var name = "new";
        var request = new EnteringRoundUsecase.Request(name, 3500);
        var roundRepository = mock(RoundRepository.class);
        var gameRepository = mock(GameRepository.class);

        var service = new EnterRoundService(
                roundRepository,
                gameRepository
        );

        doReturn(Optional.of(new Round(
                name,
                day,
                0
        )))
                .when(roundRepository)
                .findByName(eq(name));

        // when
        var round = service.enter(request);

        // then
        assertThat(round.balance()).isEqualTo(3000);
        assertThat(round.games().size()).isEqualTo(3);
    }

    @Test
    void failNotFoundRound() {
        var day = LocalDate.of(2023, 1, 21);
        var name = "new";
        var request = new EnteringRoundUsecase.Request(name, 3500);
        var roundRepository = mock(RoundRepository.class);
        var gameRepository = mock(GameRepository.class);

        var service = new EnterRoundService(
                roundRepository,
                gameRepository
        );

        // when
        when(roundRepository.findByName(name))
                .thenReturn(Optional.empty())
                .thenThrow(NotFoundRoundException.class);

        var throwable = catchThrowable(() -> service.enter(request));

        // then
        assertThat(throwable).isExactlyInstanceOf(NotFoundRoundException.class);
    }

    @Test
    void failLowMoney() {
        //given
        var day = LocalDate.of(2023, 1, 21);
        var name = "new";
        var request = new EnteringRoundUsecase.Request(name, 800);
        var roundRepository = mock(RoundRepository.class);
        var gameRepository = mock(GameRepository.class);

        var service = new EnterRoundService(
                roundRepository,
                gameRepository
        );
        doReturn(Optional.of(new Round(
                name,
                day,
                0
        )))
                .when(roundRepository)
                .findByName(eq(name));


        // when
        var throwable = catchThrowable(() -> service.enter(request));

        // then
        assertThat(throwable).isExactlyInstanceOf(GameException.LowMoneyException.class);
    }
}