package com.example.deber_03

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate


class HabitosProvider {
    companion object{
        @RequiresApi(Build.VERSION_CODES.O)
        val listaHabitos = listOf<Habitos>(
            Habitos(
                "Dormir a las 11:00 pm",
                LocalDate.now().minusDays(3),
                "Es importante para la salud dormir temprano",
                "https://cdn-icons-png.freepik.com/512/5289/5289675.png"

            ),
            Habitos(
                "Beber 2 litros de agua al día",
                LocalDate.now().minusWeeks(1),
                "Es esencial para la hidratación del cuerpo",
                "https://www.shareicon.net/data/256x256/2015/09/15/101562_incorrect_512x512.png"
            ),

            Habitos(
                "Meditar 10 minutos al día",
                LocalDate.now().minusDays(3),
                "Mejora la concentración y reduce el estrés",
                "https://cdn.icon-icons.com/icons2/1144/PNG/512/progressarrows_81035.png"
            ),

            Habitos(
                "Realizar ejercicio 30 min al día",
                LocalDate.now().minusDays(6),
                "Mejora la concentración y reduce el estrés",
                "https://cdn-icons-png.freepik.com/512/5289/5289675.png"
            ),

            Habitos(
                "Establecer metas y objetivos realistas",
                LocalDate.now().minusDays(1),
                "Mejora la atención",
                "https://cdn-icons-png.freepik.com/512/5289/5289675.png"
            ),
            Habitos(
                "Beber 2 litros de agua al día",
                LocalDate.now().minusWeeks(1),
                "Es esencial para la hidratación del cuerpo",
                "https://www.shareicon.net/data/256x256/2015/09/15/101562_incorrect_512x512.png"
            ),

            Habitos(
                "Meditar 10 minutos al día",
                LocalDate.now().minusDays(3),
                "Mejora la concentración y reduce el estrés",
                "https://cdn.icon-icons.com/icons2/1144/PNG/512/progressarrows_81035.png"
            ),

            Habitos(
                "Realizar ejercicio 30 min al día",
                LocalDate.now().minusDays(6),
                "Mejora la concentración y reduce el estrés",
                "https://cdn-icons-png.freepik.com/512/5289/5289675.png"
            ),

            Habitos(
                "Establecer metas y objetivos realistas",
                LocalDate.now().minusDays(1),
                "Mejora la atención",
                "https://cdn-icons-png.freepik.com/512/5289/5289675.png"
            )

        )
    }
}