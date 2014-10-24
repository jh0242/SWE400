import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import data_gateway.UserFriendRequestGateway;


public class UserFriendRequestTest {

	@Test
	public void testInsertFriend() throws SQLException {
		int user1 = 1594;
		int user2 = 1595;
		
		assertTrue(UserFriendRequestGateway.insertFriendRequest(user1, user2));
		assertFalse((UserFriendRequestGateway.insertFriendRequest(user1, user2)));
		assertTrue(UserFriendRequestGateway.removeFriendRequest(user2));	
	}

}
