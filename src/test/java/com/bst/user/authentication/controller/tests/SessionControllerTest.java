package com.bst.user.authentication.controller.tests;

import static com.bst.utility.testlib.SnapshotListener.expect;

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

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UserService userService;

	private HttpHeaders requestHeaders;

	private void useSessionId(HttpHeaders headers) {
		List<String> cookies = headers.get("Cookie");
		if (cookies == null) {
			cookies = headers.get("Set-Cookie");
		}
		String cookie = cookies.get(cookies.size() - 1);
		int start = cookie.indexOf('=');
		int end = cookie.indexOf(';');

		String sessionId = cookie.substring(start + 1, end);
		requestHeaders = new HttpHeaders();
		requestHeaders.add("Cookie", "JSESSIONID=" + sessionId);
	}

	@Test
	public void sessionStateMachineCheck() throws Exception {

		UserConfirmationDTO dto = new UserConfirmationDTO();
		dto.setEmail("john@doe.com");
		dto.setPassword("password");

		userService.createUser(dto.getEmail(), "John Doe", dto.getPassword());

		ResponseEntity<Person> getResponse = testRestTemplate.getForEntity("/auth/session", Person.class);
		useSessionId(getResponse.getHeaders());
		expect(getResponse.getStatusCode()).toMatchSnapshot();
		expect(getResponse.getBody()).toMatchSnapshot();

		ResponseEntity<Person> postResponse = testRestTemplate.exchange("/auth/session", HttpMethod.POST,
				new HttpEntity<>(dto, requestHeaders), Person.class);
		expect(postResponse.getStatusCode()).toMatchSnapshot();
		expect(postResponse.getBody()).toMatchSnapshot();

		getResponse = testRestTemplate.exchange("/auth/session", HttpMethod.GET, new HttpEntity<>(requestHeaders),
				Person.class);
		expect(getResponse.getStatusCode()).toMatchSnapshot();
		expect(getResponse.getBody()).toMatchSnapshot();

		ResponseEntity<Person> deleteResponse = testRestTemplate.exchange("/auth/session", HttpMethod.DELETE,
				new HttpEntity<>(dto, requestHeaders), Person.class);
		expect(deleteResponse.getStatusCode()).toMatchSnapshot();
		expect(deleteResponse.getBody()).toMatchSnapshot();

		getResponse = testRestTemplate.exchange("/auth/session", HttpMethod.GET, new HttpEntity<>(requestHeaders),
				Person.class);
		expect(getResponse.getStatusCode()).toMatchSnapshot();
		expect(getResponse.getBody()).toMatchSnapshot();

		deleteResponse = testRestTemplate.exchange("/auth/session", HttpMethod.DELETE,
				new HttpEntity<>(dto, requestHeaders), Person.class);
		expect(deleteResponse.getStatusCode()).toMatchSnapshot();
		expect(deleteResponse.getBody()).toMatchSnapshot();
	}

}
