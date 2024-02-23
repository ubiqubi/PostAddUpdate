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
    val likes: Likes,
    val comments: Comments
)

// Класс, представляющий комментарии
data class Comments(
    val count: Int, // количество комментариев
    val canPost: Boolean, // информация о том, может ли текущий пользователь комментировать запись
    val groupsCanPost: Boolean, // информация о том, могут ли сообщества комментировать запись
    val canClose: Boolean, // может ли текущий пользователь закрыть комментарии к записи
    val canOpen: Boolean // может ли текущий пользователь открыть комментарии к записи
)


// Класс, представляющий отметки "Мне нравится"
data class Likes(
    val count: Int, // число пользователей, которым понравилась запись
    val userLikes: Boolean, // наличие отметки "мне нравится" от текущего пользователя
    val canLike: Boolean, // информация о том, может ли текущий пользователь поставить отметку "мне нравится"
    val canPublish: Boolean // информация о том, может ли текущий пользователь сделать репост записи
)

// Объект-сервис для работы с записями
object WallService {
    private var posts = emptyArray<Post>() // массив, хранящий все посты
    private var nextId = 1 // переменная для хранения следующего уникального id
    fun clear() {
        posts = emptyArray()
        nextId = 1
    }

    // метод для добавления поста в массив
    fun add(post: Post): Post {
        val newPost = post.copy(id = nextId) // копируем пост и присваиваем ему новый id
        posts += newPost // добавляем пост в массив
        //posts.add(newPost) // добавляем пост в массив
        nextId++ // увеличиваем счетчик следующего id
        return newPost // возвращаем пост с новым id
    }

    // метод для обновления записи
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

            )
            return true // возвращаем true, чтобы указать успешное обновление записи
        }

        return false // возвращаем false, если запись с таким id не найдена
    }
}

fun main() {
    val likes = Likes (1,true,true,true)
    val comments = Comments  (1,true,true,true,true)
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
        comments = comments,
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
}
