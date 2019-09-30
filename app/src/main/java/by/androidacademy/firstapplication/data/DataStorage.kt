package by.androidacademy.firstapplication.data

import by.androidacademy.firstapplication.R

object DataStorage {

    fun getMoviesList(): List<Movie> {
        return listOf(
            Movie(
                "Jurassic World - Fallen Kingdom",
                R.drawable.jurassic_world_fallen_kingdom,
                R.drawable.jurassic_world_fallen_kingdom_big,
                "Three years after the demise of Jurassic World, a volcanic eruption threatens the remaining dinosaurs on the isla Nublar, so Claire Dearing, the former park manager, recruits Owen Grady to help prevent the extinction of the dinosaurs once again",
                "June 22, 2018",
                "https://youtu.be/vn9mMeWcgoM"
            ),

            Movie(
                "The Meg",
                R.drawable.the_meg,
                R.drawable.the_meg_big,
                "A deep sea submersible pilot revisits his past fears in the Mariana Trench, and accidentally unleashes the seventy foot ancestor of the Great White Shark believed to be extinct",
                "August 10, 2018",
                "https://youtu.be/GGYXExfKhmo"
            ),

            Movie(
                "The First Purge",
                R.drawable.the_first_purge,
                R.drawable.the_first_purge_big,
                "To push the crime rate below one percent for the rest of the year, the New Founding Fathers of America test a sociological theory that vents aggression for one night in one isolated community. But when the violence of oppressors meets the rage of the others, the contagion will explode from the trial-city borders and spread across the nation",
                "July 4, 2018",
                "https://youtu.be/UL29y0ah92w"
            ),

            Movie(
                "Deadpool 2",
                R.drawable.deadpool_2,
                R.drawable.deadpool_2_big,
                "Wisecracking mercenary Deadpool battles the evil and powerful Cable and other bad guys to save a boy's life",
                "May 18, 2018",
                "https://youtu.be/20bpjtCbCz0"
            ),

            Movie(
                "Black Panther",
                R.drawable.black_panther,
                R.drawable.black_panther_big,
                "King T'Challa returns home from America to the reclusive, technologically advanced African nation of Wakanda to serve as his country's new leader. However, T'Challa soon finds that he is challenged for the throne by factions within his own country as well as without. Using powers reserved to Wakandan kings, T'Challa assumes the Black Panther mantel to join with girlfriend Nakia, the queen-mother, his princess-kid sister, members of the Dora Milaje (the Wakandan 'special forces') and an American secret agent, to prevent Wakanda from being dragged into a world war",
                "February 16, 2018",
                "https://youtu.be/xjDjIWPwcPU"
            ),

            Movie(
                "Ocean's Eight",
                R.drawable.ocean_eight,
                R.drawable.ocean_eight_big,
                "Debbie Ocean, a criminal mastermind, gathers a crew of female thieves to pull off the heist of the century at New York's annual Met Gala",
                "June 8, 2018",
                "https://youtu.be/MFWF9dU5Zc0"
            ),

            Movie(
                "Interstellar",
                R.drawable.interstellar,
                R.drawable.interstellar_big,
                "Interstellar chronicles the adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage",
                "November 7, 2014",
                "https://youtu.be/zSWdZVtXT7E"
            ),

            Movie(
                "Thor - Ragnarok",
                R.drawable.thor_ragnarok,
                R.drawable.thor_ragnarok_big,
                "Thor is on the other side of the universe and finds himself in a race against time to get back to Asgard to stop Ragnarok, the prophecy of destruction to his homeworld and the end of Asgardian civilization, at the hands of an all-powerful new threat, the ruthless Hela",
                "November 3, 2017",
                "https://youtu.be/ue80QwXMRHg"
            ),

            Movie(
                "Guardians of the Galaxy",
                R.drawable.guardians_of_the_galaxy,
                R.drawable.guardians_of_the_galaxy_big,
                "Light years from Earth, 26 years after being abducted, Peter Quill finds himself the prime target of a manhunt after discovering an orb wanted by Ronan the Accuser",
                "August 1, 2014",
                "https://youtu.be/d96cjJhvlMA"
            )
        )
    }
}