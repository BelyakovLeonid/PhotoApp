package com.example.photoapp

import android.app.Application
import com.example.photoapp.data.db.PhotosCache
import com.example.photoapp.data.db.UnsplashDataBase
import com.example.photoapp.data.network.UnsplashApiService
import com.example.photoapp.data.repository.UnsplashRepository
import com.example.photoapp.ui.fragments.collection.detail.CollectionDetailFragment
import com.example.photoapp.ui.fragments.collection.list.CollectionListFragment
import com.example.photoapp.ui.fragments.collection.search.SearchCollectionFragment
import com.example.photoapp.ui.fragments.photo.detail.PhotoDetailFragment
import com.example.photoapp.ui.fragments.photo.list.PhotoListFragment
import com.example.photoapp.ui.fragments.photo.random.RandomPhotoFragment
import com.example.photoapp.ui.fragments.photo.search.SearchPhotoFragment
import com.example.photoapp.ui.viewmodels.collection.list.CollectionListViewModelFactory
import com.example.photoapp.ui.viewmodels.photo.detail.PhotoDetailViewModelFactory
import com.example.photoapp.ui.viewmodels.photo.list.PhotoListViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class PhotoApp : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@PhotoApp))

        bind() from singleton { UnsplashDataBase(instance()) }
        bind() from singleton { instance<UnsplashDataBase>().photoDao() }
        bind() from singleton { instance<UnsplashDataBase>().collectionDao() }
        bind() from singleton { UnsplashApiService() }
        bind() from singleton { PhotosCache(instance(), instance()) }
        bind() from singleton { UnsplashRepository(instance(), instance()) }

        bind(tag = PhotoListFragment::class.java.name) from provider {
            PhotoListViewModelFactory(instance<UnsplashRepository>()::getPhotosList)
        }

        bind(tag = CollectionDetailFragment::class.java.name) from provider {
            PhotoListViewModelFactory(instance<UnsplashRepository>()::getCollectionPhotos)
        }

        bind(tag = SearchPhotoFragment::class.java.name) from provider {
            PhotoListViewModelFactory(instance<UnsplashRepository>()::searchPhotos)
        }

        bind(tag = CollectionListFragment::class.java.name) from provider {
            CollectionListViewModelFactory(instance<UnsplashRepository>()::getCollectionsList)
        }

        bind(tag = SearchCollectionFragment::class.java.name) from provider {
            CollectionListViewModelFactory(instance<UnsplashRepository>()::searchCollections)
        }

        bind(tag = PhotoDetailFragment::class.java.name) from provider {
            PhotoDetailViewModelFactory(instance<UnsplashRepository>()::getPhoto)
        }

        bind(tag = RandomPhotoFragment::class.java.name) from provider {
            PhotoDetailViewModelFactory(instance<UnsplashRepository>()::getRandomPhoto)
        }


    }

    override fun onCreate() {
        super.onCreate()
    }
}