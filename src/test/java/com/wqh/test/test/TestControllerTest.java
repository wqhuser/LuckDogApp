package com.wqh.test.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.wqh.test.common.exception.UnmatchingException;
import com.wqh.test.service.TestService;
import com.wqh.test.ui.TestController;

public class TestControllerTest {
	@Test
	public void shouldShowTitle() throws Exception {
		TestService service = new TestService();
		String title = service.getTitle(1);
		if(!title.equals("Hello world")) 
			throw new UnmatchingException("Title is not matched");
		TestController controller = new TestController(service);
		Map<String, Object> model = new HashMap<String, Object>();
		String view = controller.showHomePage(model);
		if(!view.equals("home"))
			throw new UnmatchingException("View is not matched");
	}
}
