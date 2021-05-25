package net.xdclass.online_xdclass;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Assert;
import net.xdclass.online_xdclass.model.entity.User;
import net.xdclass.online_xdclass.utils.JWTUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OnlineXdclassApplicationTests {

	@Test
	public void testGeneJwt(){
		User user = new User();
		user.setId(11);
		user.setName("小明");
		user.setHeadImg("aaa.jpg");
		String token = JWTUtils.geneJsonWebToken(user);
		System.out.println(token);
//		try {
//			Thread.sleep(3000L);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		Claims claims = JWTUtils.checkJWT(token);
		System.out.println(claims.get("name"));

		Assert.isTrue("小明".equals(claims.get("name")));
	}

}
