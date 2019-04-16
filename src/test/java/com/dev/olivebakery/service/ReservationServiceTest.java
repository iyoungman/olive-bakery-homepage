package com.dev.olivebakery.service;

import com.dev.olivebakery.domain.dto.ReservationDto;
import com.dev.olivebakery.domain.enums.ReservationType;
import com.dev.olivebakery.repository.ReservationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by YoungMan on 2019-04-08.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceTest {

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	ReservationService reservationService;

	Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.create();

	@Test
	public void saveReservation() throws Exception {

		//given
		LinkedHashMap<String, Integer> maps = new LinkedHashMap<>();
		maps.put("소보로빵", 4);//빵-개수
		maps.put("죽빵", 6);//빵-개수

		LocalDateTime bringTime = LocalDate.now()
				.plusDays(1)
				.atTime(9,9,9);

		ReservationDto.SaveRequest saveRequest = ReservationDto.SaveRequest.builder()
				.bringTime(bringTime)
				.userEmail("chunso@email.com")
				.breadInfo(maps)
				.build();

		//when
		reservationService.saveReservation(saveRequest);
	}

	@Test
	public void updateReservationType() {
	}

	@Test
	public void getReservationInfos() throws Exception, JsonProcessingException {

		//given
		final String email = "testemail";
		final ReservationType reservationType = ReservationType.REQUEST;

		//when
		List<ReservationDto.GetResponse> getResponses = reservationService.getReservationInfos(email, reservationType);

		//then
		for (ReservationDto.GetResponse s : getResponses) {
			System.out.println(gson.toJson(s));
		}
	}

	@Test
	public void getReservationInfoRecently() throws Exception {

		//given
		final String email = "testemail";

		//when
		ReservationDto.GetResponse getResponse = reservationService.getReservationInfoByRecently(email);

		//then
		System.out.println(getResponse.toString());
	}

	@Test
	public void getReservationInfosByDate() throws Exception {

		//given
		ReservationDto.DateRequest dateRequestDto = ReservationDto.DateRequest.builder()
				.reservationType(ReservationType.REQUEST)
				.selectDate(LocalDate.of(2019, 3, 24))
				.build();

		//when
		List<ReservationDto.GetResponse> getResponses = reservationService.getReservationInfosByDate(dateRequestDto);

		//then
		getResponses.stream().forEach(s -> System.out.println(s.toString()));
	}

	@Test
	public void getReservationInfosByDateRange() throws Exception {

		//given
		ReservationDto.DateRangeRequest dateRangeRequest = ReservationDto.DateRangeRequest.builder()
				.reservationType(ReservationType.REQUEST)
				.startDate(LocalDate.of(2019, 3, 24))
				.endDate(LocalDate.of(2019, 3, 30))
				.build();

		//when
		List<ReservationDto.GetResponse> getDtos = reservationService.getReservationInfosByDateRange(dateRangeRequest);

		//then
		getDtos.stream().forEach(s -> System.out.println(s.toString()));
	}

	@Test
	public void timeValidationCheck() throws Exception {

		//given
		LocalDateTime validBringTime = LocalDate.now()
				.plusDays(1)
				.atTime(12,12,12);

		LocalDateTime unValidBringTimeByBefore = LocalDateTime.now()
				.minusHours(3);

		LocalDateTime unValidBringTimeByEfter20 = LocalDate.now()
				.atTime(20,20,20);

		//then
		reservationService.timeValidationCheck(validBringTime);
		reservationService.timeValidationCheck(unValidBringTimeByBefore);
		reservationService.timeValidationCheck(unValidBringTimeByEfter20);

	}

	@Test
	public void saveReservationSaleByDate() throws Exception {

		//then
		reservationService.saveReservationSaleByDate();
		System.out.println();

	}

}