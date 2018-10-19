package com.bst.user.authentication.controller.tests;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bst.configuration.user.authentication.UserAuthenticationConfiguration;
import com.bst.user.authentication.components.UserService;
import com.bst.user.authentication.dto.UserConfirmationDTO;
import com.bst.user.authentication.entities.Person;
import com.bst.utility.testlib.SnapshotListener;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { UserAuthenticationConfiguration.class, WebSecurityConfiguration.class })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(listeners = SnapshotListener.class, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@TestPropertySource("classpath:session-controller-test.properties")
public class SessionControllerTest {

	private HttpHeaders requestHeaders;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UserService userService;

	@Test
	public void sessionStateMachineCheck() throws Exception {

		final UserConfirmationDTO dto = new UserConfirmationDTO();
		dto.setEmail("john@doe.com");
		dto.setPassword("password");

		this.userService.createUser(dto.getEmail(), "John Doe", dto.getPassword());

		ResponseEntity<Person> getResponse = this.testRestTemplate.getForEntity("/auth/session", Person.class);
		this.useSessionId(getResponse.getHeaders());
		SnapshotListener.expect(getResponse.getStatusCode()).toMatchSnapshot();
		SnapshotListener.expect(getResponse.getBody()).toMatchSnapshot();

		final ResponseEntity<Person> postResponse = this.testRestTemplate.exchange("/auth/session", HttpMethod.POST,
				new HttpEntity<>(dto, this.requestHeaders), Person.class);
		SnapshotListener.expect(postResponse.getStatusCode()).toMatchSnapshot();
		SnapshotListener.expect(postResponse.getBody()).toMatchSnapshot();

		getResponse = this.testRestTemplate.exchange("/auth/session", HttpMethod.GET,
				new HttpEntity<>(this.requestHeaders), Person.class);
		SnapshotListener.expect(getResponse.getStatusCode()).toMatchSnapshot();
		SnapshotListener.expect(getResponse.getBody()).toMatchSnapshot();

		ResponseEntity<Person> deleteResponse = this.testRestTemplate.exchange("/auth/session", HttpMethod.DELETE,
				new HttpEntity<>(dto, this.requestHeaders), Person.class);
		SnapshotListener.expect(deleteResponse.getStatusCode()).toMatchSnapshot();
		SnapshotListener.expect(deleteResponse.getBody()).toMatchSnapshot();

		getResponse = this.testRestTemplate.exchange("/auth/session", HttpMethod.GET,
				new HttpEntity<>(this.requestHeaders), Person.class);
		SnapshotListener.expect(getResponse.getStatusCode()).toMatchSnapshot();
		SnapshotListener.expect(getResponse.getBody()).toMatchSnapshot();

		deleteResponse = this.testRestTemplate.exchange("/auth/session", HttpMethod.DELETE,
				new HttpEntity<>(dto, this.requestHeaders), Person.class);
		SnapshotListener.expect(deleteResponse.getStatusCode()).toMatchSnapshot();
		SnapshotListener.expect(deleteResponse.getBody()).toMatchSnapshot();
	}

	private void useSessionId(final HttpHeaders headers) {
		List<String> cookies = headers.get("Cookie");
		if (cookies == null) {
			cookies = headers.get("Set-Cookie");
		}
		final String cookie = cookies.get(cookies.size() - 1);
		final int start = cookie.indexOf('=');
		final int end = cookie.indexOf(';');

		final String sessionId = cookie.substring(start + 1, end);
		this.requestHeaders = new HttpHeaders();
		this.requestHeaders.add("Cookie", "JSESSIONID=" + sessionId);
	}

}
