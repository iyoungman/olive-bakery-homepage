package com.dev.olivebakery.service.reservationService;

import com.dev.olivebakery.exception.UserDefineException;
import com.dev.olivebakery.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Created by YoungMan on 2019-05-21.
 */

@Service
@RequiredArgsConstructor
public class ReservationDeleteService {

	private final ReservationRepository reservationRepository;


	/**
	 * 예약 삭제
	 */
	public void deleteReservation(Long reservationId, String bearerToken) {
		String findEmail = reservationRepository.getMemberEmailByReservationId(reservationId);
		checkValidateEmail(findEmail, bearerToken);

		reservationRepository.deleteById(reservationId);
	}

	private void checkValidateEmail(String findEmail, String bearerToken) {
		if(!findEmail.equals(bearerToken)) {
			throw new UserDefineException("예약자와 삭제자가 일치하지 않습니다.");
		}
	}
}
