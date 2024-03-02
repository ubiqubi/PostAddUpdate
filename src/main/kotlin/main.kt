import WallService.createComment


// Код ниже представляет класс Post, который представляет пост.
data class Post(
    val id: Int, // идентификатор записи
    val toId: Int, // идентификатор владельца стены
    val fromId: Int, // идентификатор автора записи
    val createdBy: Int, // идентификатор администратора, который опубликовал запись
    val date: Int, // время публикации записи в формате unixtime
    val text: String, // текст записи
    val replyOwnerId: Int, // идентификатор владельца записи, на которую была оставлена текущая
    val replyPostId: Int, // идентификатор записи, на которую была оставлена текущая
    val friendsOnly: Boolean, // если запись была создана с опцией "только для друзей"
    val original: Post?,
    val likes: Likes,
    val comments: Array<Comments>, // Массив с комментами
    val attachments: Array<Attachment>, // Массив attachments

)

interface Attachment {
    val type: String
}

// Класс, представляющий комментарии
data class Comments(
    val cid: Int, // Идентификатор комментария
    val from_id: Int, // Идентификатор автора комментария.
    val date: Int, // Дата создания комментария в формате Unixtime
    val text: String, // текст комментария
    val attachments: Array<Attachment>, // Массив attachments
)


// Класс, представляющий отметки "Мне нравится"
data class Likes(
    val count: Int, // число пользователей, которым понравилась запись
    val userLikes: Boolean, // наличие отметки "мне нравится" от текущего пользователя
    val canLike: Boolean, // информация о том, может ли текущий пользователь поставить отметку "мне нравится"
    val canPublish: Boolean // информация о том, может ли текущий пользователь сделать репост записи
)

data class PhotoAttachment(
    val photo: Photo
) : Attachment {
    override val type = "photo"
}

data class Photo(
    val id: Int,
    val album_id: Int,
    val owner_id: Int,
    val user_id: Int,
    val text: String,
    val date: Int,
    val width: Int,
    val height: Int
)

data class AudioAttachment(
    val audio: Audio
) : Attachment {
    override val type = "audio"
}

data class Audio(
    val id: Int,
    val owner_id: Int,
    val artist: String,
    val title: String,
    val duration: Int,
    val url: String,
    val lyrics_id: Int,
    val album_id: Int,
    val genre_id: Int,
    val date: Int,
    val no_search: Int,
    val is_hq: Int
)

data class VideoAttachment(
    val video: Video
) : Attachment {
    override val type = "video"
}

data class Video(
    val id: Int,
    val owner_id: Int,
    val title: String,
    val description: String,
    val duration: Int
)

data class DocumentAttachment(
    val document: Document
) : Attachment {
    override val type = "document"
}

data class Document(
    val id: Int,
    val owner_id: Int,
    val title: String,
    val size: Int,
    val ext: String,
    val url: String,
    val date: Int,
)

data class LinkAttachment(
    val link: Link
) : Attachment {
    override val type = "link"
}

data class Link(
    val url: String,
    val title: String,
    val caption: String,
    val description: String,
    val photo: Any,
    val preview_url: String
)

// Класс PostNotFoundException, наследуемый от класса Exception
data class PostNotFoundException(override val message: String?) : Exception(message)

// Объект-сервис для работы с записями
object WallService {
    private var posts = emptyArray<Post>() // массив, хранящий все посты
    private var nextId = 1 // переменная для хранения следующего уникального id
    private var comments = emptyArray<Comments>()
    fun clear() {
        comments = emptyArray<Comments>()
        posts = emptyArray()
        nextId = 1
    }

    // Функция для добавления поста в массив
    fun add(post: Post): Post {
        val newPost = post.copy(id = nextId) // копируем пост и присваиваем ему новый id
        posts += newPost // добавляем пост в массив
        //posts.add(newPost) // добавляем пост в массив
        nextId++ // увеличиваем счетчик следующего id
        return newPost // возвращаем пост с новым id
    }

    // Функция для создания комментария к посту
    fun createComment(postId: Int, comment: Comments): Array<Comments> {
        // Проверяем, существует ли в массиве posts пост с id равным postId
        val post = posts.find { it.id == postId }
        if (post != null) {
            // Добавляем комментарий в массив comments
            comments += comment
            return comments
        } else {
            throw PostNotFoundException("Пост ID $postId не найден")
        }
    }

    // Функция для обновления записи
    fun update(post: Post): Boolean {
        val index = posts.indexOfFirst { it.id == post.id } // ищем индекс записи с id, соответствующим post

        if (index != -1) {
            // запись найдена, обновляем все свойства
            posts[index] = post.copy(
                toId = post.toId,
                fromId = post.fromId,
                createdBy = post.createdBy,
                date = post.date,
                text = post.text,
                replyOwnerId = post.replyOwnerId,
                replyPostId = post.replyPostId,
                friendsOnly = post.friendsOnly,
                likes = post.likes,
                comments = post.comments,
                attachments = post.attachments
            )
            return true // возвращаем true, чтобы указать успешное обновление записи
        }
        return false // возвращаем false, если запись с таким id не найдена
    }
}

fun main() {
    // Добавляем файлы в архив к посту
    val photo = Photo(1, 1, 1, 1, "Photo 1", 123456789, 800, 600)
    val newAttachments: Array<Attachment> = arrayOf(PhotoAttachment(photo = photo))
    val likes = Likes(1, true, true, true)

    // Пример использования методов add и update
    val post1 = Post(
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
        comments = emptyArray(),
        original = null,
        attachments = newAttachments
    )

    // Добавляем пост
    val addedPost = WallService.add(post1)
    println("Добавленный пост: ${addedPost.text}")


    // Обновляем пост
    val updatedPost = addedPost.copy(text = "Привет! Это обновленный пост.")
    val isUpdated = WallService.update(updatedPost)

    if (isUpdated) {
        println("Пост успешно обновлен: ${updatedPost.text}")
    } else {
        println("Не удалось обновить пост с id ${updatedPost.id}")
    }

    // Создаем комментарий
    val comment1 = Comments(1, 1, 1, "Классный пост!", newAttachments)
    // добавление комментария в пост
    val addComPost = addedPost.copy(comments = createComment(1, comment1))
    println("комментарий: ${addComPost.comments.component1().text}")

}

