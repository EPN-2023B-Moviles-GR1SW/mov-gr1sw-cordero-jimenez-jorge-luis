data class Comic(
    val id: Int,
    var titulo: String,
    var autor: String,
    var genero: String,
    var anioPublicacion: String,
    var productoraId: Int){

    override fun toString(): String {
        return "$id,$titulo,$autor,$genero,$anioPublicacion,$productoraId"
    }
}