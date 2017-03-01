package com.tubic.testapp.di.components;

import com.tubic.testapp.data.source.FacebookRepository;
import com.tubic.testapp.data.source.GoogleSearchRepository;

/**
 * Created by ovitaliy on 27.02.2017.
 */

public interface RepositoriesComponent {

    GoogleSearchRepository googleSearchRepository();

    FacebookRepository facebookRepository();
}
