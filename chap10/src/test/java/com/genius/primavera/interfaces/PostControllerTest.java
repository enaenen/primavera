package com.genius.primavera.interfaces;

import com.genius.primavera.application.post.PostService;
import com.genius.primavera.domain.model.post.Post;
import com.genius.primavera.domain.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PostService postService;

	@Test
	@Order(1)
	@Disabled
	@DisplayName("포스팅 목록 화면 접근")
	@WithUserDetails(value = "Genius Choi", userDetailsServiceBeanName = "primaveraUserDetailsService")
	public void postList() throws Exception {
		given(this.postService.findAll()).willReturn(List.of(
				Post.builder().id(1).subject("로마는 하루아침에 이루어지지 않았다.").contents("제1권 로마는 하루아침에 이루어지지 않았다.").writer(User.builder().id(1).email("Genius Choi").nickname("Genius").build()).build(),
				Post.builder().id(2).subject("한니발 전쟁").contents("제2권 한니발 전쟁").writer(User.builder().id(1).email("Genius Choi").nickname("Genius").build()).build()));
		mockMvc.perform(get("/posts/all").accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("로마는 하루아침에 이루어지지 않았다.")))
				.andExpect(content().string(containsString("Genius")));
	}

	@Test
	@Order(2)
	@DisplayName("포스팅 페이징 목록 화면 접근")
	@WithUserDetails(value = "Genius Choi", userDetailsServiceBeanName = "primaveraUserDetailsService")
	public void postListOfPagination() throws Exception {
		Pageable pageable = new PageRequest(0, 10);
		List<Post> list = List.of(
				Post.builder().id(1).subject("로마는 하루아침에 이루어지지 않았다.").contents("제1권 로마는 하루아침에 이루어지지 않았다.").writer(User.builder().id(1).email("Genius Choi").nickname("Genius").build()).build(),
				Post.builder().id(2).subject("한니발 전쟁").contents("제2권 한니발 전쟁").writer(User.builder().id(1).email("Genius Choi").nickname("Genius").build()).build(),
				Post.builder().id(3).subject("한니발 전쟁").contents("제2권 한니발 전쟁").writer(User.builder().id(1).email("Genius Choi").nickname("Genius").build()).build(),
				Post.builder().id(3).subject("한니발 전쟁").contents("제2권 한니발 전쟁").writer(User.builder().id(1).email("Genius Choi").nickname("Genius").build()).build(),
				Post.builder().id(4).subject("한니발 전쟁").contents("제2권 한니발 전쟁").writer(User.builder().id(1).email("Genius Choi").nickname("Genius").build()).build(),
				Post.builder().id(5).subject("한니발 전쟁").contents("제2권 한니발 전쟁").writer(User.builder().id(1).email("Genius Choi").nickname("Genius").build()).build(),
				Post.builder().id(6).subject("한니발 전쟁").contents("제2권 한니발 전쟁").writer(User.builder().id(1).email("Genius Choi").nickname("Genius").build()).build(),
				Post.builder().id(7).subject("한니발 전쟁").contents("제2권 한니발 전쟁").writer(User.builder().id(1).email("Genius Choi").nickname("Genius").build()).build(),
				Post.builder().id(8).subject("한니발 전쟁").contents("제2권 한니발 전쟁").writer(User.builder().id(1).email("Genius Choi").nickname("Genius").build()).build(),
				Post.builder().id(9).subject("한니발 전쟁").contents("제2권 한니발 전쟁").writer(User.builder().id(1).email("Genius Choi").nickname("Genius").build()).build(),
				Post.builder().id(10).subject("한니발 전쟁").contents("제2권 한니발 전쟁").writer(User.builder().id(1).email("Genius Choi").nickname("Genius").build()).build(),
				Post.builder().id(11).subject("한니발 전쟁").contents("제2권 한니발 전쟁").writer(User.builder().id(1).email("Genius Choi").nickname("Genius").build()).build(),
				Post.builder().id(12).subject("한니발 전쟁").contents("제2권 한니발 전쟁").writer(User.builder().id(1).email("Genius Choi").nickname("Genius").build()).build()
		);
		Page<Post> postPage = new PageImpl(list, pageable, list.size());
		given(this.postService.findForPageable(pageable)).willReturn(postPage);
		Assertions.assertEquals(10, postPage.getSize());
		Assertions.assertEquals(13, postPage.getTotalElements());
		Assertions.assertEquals(2, postPage.getTotalPages());
		Assertions.assertEquals(0, postPage.getNumber());
		Assertions.assertEquals(13, postPage.getNumberOfElements());
	}

	@Test
	@Order(3)
	@DisplayName("포스팅 상세 화면 접근")
	@WithUserDetails(value = "Genius Choi", userDetailsServiceBeanName = "primaveraUserDetailsService")
	public void postDetail() throws Exception {
		given(this.postService.findById(1)).willReturn(Post.builder().id(1).subject("제1권 로마는 하루아침에 이루어지지 않았다.").contents("제1권 로마는 하루아침에 이루어지지 않았다.").writer(User.builder().id(1).nickname("Genius").build()).build());
		mockMvc.perform(get("/posts/1").accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("하루아침에")))
				.andExpect(content().string(containsString("이루어지지")))
				.andExpect(content().string(containsString("Genius")));
	}

	@Test
	@Order(4)
	@DisplayName("포스팅 등록 화면 접근")
	@WithUserDetails(value = "Genius Choi", userDetailsServiceBeanName = "primaveraUserDetailsService")
	public void postForm() throws Exception {
		mockMvc.perform(get("/post/form")).andExpect(status().isOk());
	}

	@Test
	@Order(5)
	@DisplayName("포스팅 저장 후 목록 화면")
	@WithUserDetails(value = "Genius Choi", userDetailsServiceBeanName = "primaveraUserDetailsService")
	public void postSave() throws Exception {
		MultiValueMap params = new LinkedMultiValueMap();
		params.set("subject", "승자의 혼미");
		params.set("contents", "카르타고의 멸망에서부터 카이사르가 역사적 무대로 등장하기 전까지를 그리고 있는 <로마인 이야기> 그 세번째 이야기.");
		params.set("status", "1");
		params.set("writer.id", "1");
		mockMvc.perform(post("/post/save").params(params)).andExpect(status().is3xxRedirection());
	}
}
