package com.apap.tu06.service;

import static org.junit.Assert.assertThat;

import java.sql.Date;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.apap.tu06.model.FlightModel;
import com.apap.tu06.repository.FlightDb;

@RunWith(SpringRunner.class)
public class FlightServiceTest {
	@Autowired
	private FlightService flightService;
	
	@MockBean
	private FlightDb flightDb;
	
	@TestConfiguration // Limit scope Bean yang didefinisikan menjadi local class
	static class FlightServiceTestContextConfiguration {
		@Bean //Initiate flightServce as Bean
		public FlightService flightService() {
			return new FlightServiceImpl();
		}
	}
	
	@Test
	public void whenValidFlightNumber_thenFlightShouldBeFound() {
		//Given
		FlightModel flightModel = new FlightModel();
		flightModel.setFlightNumber("I765");
		flightModel.setOrigin("Jakarta");
		flightModel.setDestination("Bali");
		flightModel.setTime(new Date(new java.util.Date().getTime()));
		Optional<FlightModel> flight = Optional.of(flightModel);
		Mockito.when(flightDb.findByFlightNumber(flight.get().getFlightNumber())).thenReturn(flight);
		
		// When
		Optional<FlightModel> foundOptional = flightService.getFlightDetailByFlightNumber(flight.get().getFlightNumber());
		
		// Then
		assertThat(foundOptional, Matchers.notNullValue());
		assertThat(foundOptional.get().getFlightNumber(), Matchers.equalTo(flightModel.getFlightNumber()));
	}
}


