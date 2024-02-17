import junit.framework.TestCase.*
import org.junit.Test


class WallServiceTest {

    @Test
    fun WallServiceUpdateADD () {
        val post = Post(
            id = 1,
            toId = 1,
            fromId = 1,
            createdBy = 1,
            date = 1631302800,
            text = "Привет, это первый пост!",
            replyOwnerId = 1,
            replyPostId = 1,
            friendsOnly = true
        )
        val service = WallService
        service.add(post)

        assertTrue(post.id > 0)
    }




    @Test
    fun WallServiceUpdateTrue () {
val service = WallService
        service.add(Post(
            id = 1,
            toId = 1,
            fromId = 1,
            createdBy = 1,
            date = 1631302800,
            text = "Привет, это первый пост!",
            replyOwnerId = 1,
            replyPostId = 1,
            friendsOnly = true
        ))
        service.add(Post(
            id = 2,
            toId = 1,
            fromId = 1,
            createdBy = 1,
            date = 1631302800,
            text = "Привет, это первый пост!",
            replyOwnerId = 1,
            replyPostId = 1,
            friendsOnly = true
        ))
        val update = Post(
                id = 1,
            toId = 1,
            fromId = 1,
            createdBy = 1,
            date = 1631302800,
            text = "Привет, это обновленный пост!",
            replyOwnerId = 1,
            replyPostId = 1,
            friendsOnly = true
        )

        val result = service.update(update)

        assertTrue(result)
    }


    @Test
    fun WallServiceUpdateFalse () {
        val service = WallService
        service.add(Post(
            id = 1,
            toId = 1,
            fromId = 1,
            createdBy = 1,
            date = 1631302800,
            text = "Привет, это первый пост!",
            replyOwnerId = 1,
            replyPostId = 1,
            friendsOnly = true
        ))
        service.add(Post(
            id = 2,
            toId = 1,
            fromId = 1,
            createdBy = 1,
            date = 1631302800,
            text = "Привет, это первый пост!",
            replyOwnerId = 1,
            replyPostId = 1,
            friendsOnly = true
        ))
        val update = Post(
            id = 10,
            toId = 1,
            fromId = 1,
            createdBy = 1,
            date = 1631302800,
            text = "Привет, это обновленный пост!",
            replyOwnerId = 1,
            replyPostId = 1,
            friendsOnly = true
        )

        val result = service.update(update)

        assertFalse(result)
    }
}