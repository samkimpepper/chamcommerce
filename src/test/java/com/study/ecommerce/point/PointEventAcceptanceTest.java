package com.study.ecommerce.point;

import com.study.ecommerce.AcceptanceTest;
import com.study.ecommerce.member.MemberResponse;
import com.study.ecommerce.member.acceptance.MemberSteps;
import com.study.ecommerce.point.pointevent.dto.PointEventResponse;
import com.study.ecommerce.point.strategy.SignupPointStrategy;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PointEventAcceptanceTest extends AcceptanceTest {
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void createOneTimePointEvent() {
        // when
        PointEventResponse response = PointEventSteps.createPointEvent(PointEventFixture.createOneTimePointEvent(), ACCESS_TOKEN_ADMIN);
        List<PointEventResponse> pointEvents = PointEventSteps.showOngoingPointEvents(ACCESS_TOKEN_ADMIN);

        // then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getAmount()).isEqualTo(PointEventFixture.AMOUNT);
        assertThat(pointEvents.size()).isEqualTo(1);
    }

    @Test
    public void createAttendancePointEvent() {
        // when
        PointEventResponse response = PointEventSteps.createPointEvent(PointEventFixture.createWeekAttendancePointEvent(), ACCESS_TOKEN_ADMIN);
        List<PointEventResponse> pointEvents = PointEventSteps.showOngoingPointEvents(ACCESS_TOKEN_ADMIN);

        // then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getBaseAmount()).isEqualTo(PointEventFixture.BASE_AMOUNT);
        assertThat(pointEvents.size()).isEqualTo(1);
    }

    @Test
    public void participateOneTimePointEvent() {
        // given
        PointEventResponse response = PointEventSteps.createPointEvent(PointEventFixture.createOneTimePointEvent(), ACCESS_TOKEN_ADMIN);

        // when
        PointEventSteps.participate(response.getId(), ACCESS_TOKEN_CUSTOMER);
        MemberResponse member = MemberSteps.showMemberInfo(ACCESS_TOKEN_CUSTOMER);

        // then
        assertThat(member.getPoints()).isEqualTo(PointEventFixture.AMOUNT + SignupPointStrategy.POINT_AMOUNT);
    }

    @Test
    public void participateAttendancePointEvent() {
        // given
        PointEventResponse response = PointEventSteps.createPointEvent(PointEventFixture.createWeekAttendancePointEvent(), ACCESS_TOKEN_ADMIN);

        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        int expectedPoints = 0;
        for (int i = 1; i <= 7; i++) {
            clock = Clock.offset(clock, Duration.ofDays(1));

            try (MockedStatic<Clock> mockedClock = Mockito.mockStatic(Clock.class)) {
                mockedClock.when(Clock::systemDefaultZone).thenReturn(clock);
                PointEventSteps.participate(response.getId(), ACCESS_TOKEN_CUSTOMER);
            }

            expectedPoints += PointEventFixture.BASE_AMOUNT * i;
        }

        MemberResponse member = MemberSteps.showMemberInfo(ACCESS_TOKEN_CUSTOMER);
        List<PointHistoryResponse> pointHistories = PointSteps.showPointHistories(ACCESS_TOKEN_CUSTOMER);

        // then
        assertThat(member.getPoints()).isEqualTo(expectedPoints + SignupPointStrategy.POINT_AMOUNT);
        assertThat(pointHistories.size()).isEqualTo(8);
    }

    @Test
    public void participateOneTimePointEventFail() {
        // given
        PointEventResponse pointEvent = PointEventSteps.createPointEvent(PointEventFixture.createOneTimePointEvent(), ACCESS_TOKEN_ADMIN);

        // when
        PointEventSteps.participate(pointEvent.getId(), ACCESS_TOKEN_CUSTOMER);
        ExtractableResponse<Response> response = PointEventSteps.participate(pointEvent.getId(), ACCESS_TOKEN_CUSTOMER);
        MemberResponse member = MemberSteps.showMemberInfo(ACCESS_TOKEN_CUSTOMER);

        // then
        assertThat(response.statusCode()).isEqualTo(500);
        assertThat(member.getPoints()).isEqualTo(PointEventFixture.AMOUNT + SignupPointStrategy.POINT_AMOUNT);
    }

    @Test
    public void participateAttendancePointEventFail() {
        // given
        PointEventResponse pointEvent = PointEventSteps.createPointEvent(PointEventFixture.createWeekAttendancePointEvent(), ACCESS_TOKEN_ADMIN);

        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        int expectedPoints = 0;
        for (int i = 1; i <= 7; i++) {
            clock = Clock.offset(clock, Duration.ofDays(1));

            try (MockedStatic<Clock> mockedClock = Mockito.mockStatic(Clock.class)) {
                mockedClock.when(Clock::systemDefaultZone).thenReturn(clock);
                PointEventSteps.participate(pointEvent.getId(), ACCESS_TOKEN_CUSTOMER);
            }

            expectedPoints += PointEventFixture.BASE_AMOUNT * i;
        }

        ExtractableResponse<Response> response = PointEventSteps.participate(pointEvent.getId(), ACCESS_TOKEN_CUSTOMER);

        MemberResponse member = MemberSteps.showMemberInfo(ACCESS_TOKEN_CUSTOMER);
        List<PointHistoryResponse> pointHistories = PointSteps.showPointHistories(ACCESS_TOKEN_CUSTOMER);

        // then
        assertThat(response.statusCode()).isEqualTo(500);
        assertThat(member.getPoints()).isEqualTo(expectedPoints + SignupPointStrategy.POINT_AMOUNT);
        assertThat(pointHistories.size()).isEqualTo(8);
    }
}
