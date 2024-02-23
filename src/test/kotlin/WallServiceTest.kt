import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Test


class WallServiceTest {


    @Test
    fun WallServiceUpdateADD() {
        val likes = Likes(1, true, true, true)
        val comments = Comments(1, true, true, true, true)
        val post = Post(
            id = 1,
            toId = 1,
            fromId = 1,
            createdBy = 1,
            date = 1631302800,
            text = "Привет, это первый пост!",
            replyOwnerId = 1,
            replyPostId = 1,
            friendsOnly = true,
            likes = likes,
            comments = comments,
            original = null,
            attachments = emptyArray()
        )
        val service = WallService
        service.add(post)

        assertTrue(post.id > 0)
    }

    @Before
    fun clearBeforeTestTrue() {
        WallService.clear()
    }

    @Test
    fun WallServiceUpdateTrue() {
        val likes = Likes(1, true, true, true)
        val comments = Comments(1, true, true, true, true)
        val service = WallService
        service.add(
            Post(
                id = 1,
                toId = 1,
                fromId = 1,
                createdBy = 1,
                date = 1631302800,
                text = "Привет, это первый пост!",
                replyOwnerId = 1,
                replyPostId = 1,
                friendsOnly = true,
                likes = likes,
                comments = comments,
                original = null,
                attachments = emptyArray()
            )
        )
        service.add(
            Post(
                id = 2,
                toId = 1,
                fromId = 1,
                createdBy = 1,
                date = 1631302800,
                text = "Привет, это первый пост!",
                replyOwnerId = 1,
                replyPostId = 1,
                friendsOnly = true,
                likes = likes,
                comments = comments,
                original = null,
                attachments = emptyArray()
            )
        )
        val update = Post(
            id = 1,
            toId = 1,
            fromId = 1,
            createdBy = 1,
            date = 1631302800,
            text = "Привет, это обновленный пост!",
            replyOwnerId = 1,
            replyPostId = 1,
            friendsOnly = true,
            likes = likes,
            comments = comments,
            original = null,
            attachments = emptyArray()
        )

        val result = service.update(update)

        assertTrue(result)
    }


    @Test
    fun WallServiceUpdateFalse() {
        val likes = Likes(1, true, true, true)
        val comments = Comments(1, true, true, true, true)
        val service = WallService
        service.add(
            Post(
                id = 1,
                toId = 1,
                fromId = 1,
                createdBy = 1,
                date = 1631302800,
                text = "Привет, это первый пост!",
                replyOwnerId = 1,
                replyPostId = 1,
                friendsOnly = true,
                likes = likes,
                comments = comments,
                original = null,
                attachments = emptyArray()
            )
        )
        service.add(
            Post(
                id = 2,
                toId = 1,
                fromId = 1,
                createdBy = 1,
                date = 1631302800,
                text = "Привет, это первый пост!",
                replyOwnerId = 1,
                replyPostId = 1,
                friendsOnly = true,
                likes = likes,
                comments = comments,
                original = null,
                attachments = emptyArray()
            )
        )
        val update = Post(
            id = 10,
            toId = 1,
            fromId = 1,
            createdBy = 1,
            date = 1631302800,
            text = "Привет, это обновленный пост!",
            replyOwnerId = 1,
            replyPostId = 1,
            friendsOnly = true,
            likes = likes,
            comments = comments,
            original = null,
            attachments = emptyArray()
        )

        val result = service.update(update)

        assertFalse(result)
    }
}
