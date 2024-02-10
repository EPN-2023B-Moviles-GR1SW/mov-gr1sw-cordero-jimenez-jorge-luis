data class Productora(
    val id: Int,
    var nombre: String,
    var pais: String,
    var fundacion: String,
    var sitioWeb: String){

    override fun toString(): String {
        return "$id,$nombre,$pais,$fundacion,$sitioWeb"
    }
}
