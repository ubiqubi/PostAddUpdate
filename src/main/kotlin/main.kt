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
    val friendsOnly: Boolean // если запись была создана с опцией "только для друзей"
)

// Объект-сервис для работы с записями
object WallService {
    private var posts = arrayListOf<Post>() // массив, хранящий все посты
    private var nextId = 0 // переменная для хранения следующего уникального id

    // метод для добавления поста в массив
    fun add(post: Post): Post {
        val newPost = post.copy(id = nextId) // копируем пост и присваиваем ему новый id
        posts.add(newPost) // добавляем пост в массив
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
                friendsOnly = post.friendsOnly
            )
            return true // возвращаем true, чтобы указать успешное обновление записи
        }

        return false // возвращаем false, если запись с таким id не найдена
    }
}

fun main() {
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
        friendsOnly = true
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
