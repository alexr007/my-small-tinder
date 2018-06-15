package ua.danit.dao;


import ua.danit.model.UserDemo;
import ua.danit.utils.GeneratorID;

import java.util.ArrayList;

public class UsersDAO extends ArrayList<UserDemo> {

    public UsersDAO() {
        add(new UserDemo("Mrs.Hudson",
                "https://vignette.wikia.nocookie.net/heroes-v-villains/images" +
                        "/f/fb/221BLandlady.jpg/revision/latest?cb=20180205030835"));
        add(new UserDemo("Your Ex",
                "https://www.tuinverbeelding.nl/wp-content/uploads/2014/04/LW01LM.jpg"));
        add(new UserDemo("Stifler's Mom",
                "https://pbs.twimg.com/profile_images/2282058605/image.jpg"));
        add(new UserDemo("Original Ba XX",
                "https://pp.userapi.com/c424519/v424519551/3a16/sVFACC5H_68.jpg"));
        add(new UserDemo("Not First Woman In Space",
                "http://www.unvienna.org/sdgs/img/UNOOSA/Female-astronaut-training---NASA_1-1.jpg"));
    }
}
