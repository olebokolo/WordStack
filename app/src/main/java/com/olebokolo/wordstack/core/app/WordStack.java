package com.olebokolo.wordstack.core.app;

import android.app.Application;

import com.olebokolo.wordstack.core.cards.CardsService;
import com.olebokolo.wordstack.core.languages.factory.LanguageComponentsFactory;
import com.olebokolo.wordstack.core.languages.factory.LanguageComponentsFactoryImpl;
import com.olebokolo.wordstack.core.resources.factory.DrawableComponentsFactory;
import com.olebokolo.wordstack.core.resources.factory.DrawableComponentsFactoryImpl;
import com.olebokolo.wordstack.core.user.settings.factory.UserSettingsComponentsFactory;
import com.olebokolo.wordstack.core.user.settings.factory.UserSettingsComponentsFactoryImpl;
import com.olebokolo.wordstack.core.utils.TypefaceCollection;
import com.olebokolo.wordstack.core.utils.TypefaceManager;
import com.olebokolo.wordstack.presentation.activities.ChoosePracticeStacksActivity;
import com.olebokolo.wordstack.presentation.activities.GreetingActivity;
import com.olebokolo.wordstack.presentation.activities.LanguagesActivity;
import com.olebokolo.wordstack.presentation.activities.MainMenuActivity;
import com.olebokolo.wordstack.presentation.activities.PracticeActivity;
import com.olebokolo.wordstack.presentation.activities.SettingsActivity;
import com.olebokolo.wordstack.presentation.activities.StackActivity;
import com.olebokolo.wordstack.presentation.activities.StackListActivity;
import com.olebokolo.wordstack.presentation.dialogs.CardAddDialog;
import com.olebokolo.wordstack.presentation.dialogs.CardEditDialog;
import com.olebokolo.wordstack.presentation.dialogs.PracticeChooseFaceSideDialog;
import com.olebokolo.wordstack.presentation.dialogs.PracticeFinishedDialog;
import com.olebokolo.wordstack.presentation.dialogs.StackActionsDialog;
import com.olebokolo.wordstack.presentation.dialogs.StackAddDialog;
import com.olebokolo.wordstack.presentation.dialogs.StackAlert;
import com.olebokolo.wordstack.presentation.dialogs.StackConfirmDeleteDialog;
import com.olebokolo.wordstack.presentation.dialogs.StackEditDialog;
import com.olebokolo.wordstack.presentation.lists.cards.CardAdapter;
import com.olebokolo.wordstack.presentation.lists.cards.CardItemAdapter;
import com.olebokolo.wordstack.presentation.lists.stacks.PracticeStackItemAdapter;
import com.olebokolo.wordstack.presentation.lists.stacks.StackAdapter;
import com.olebokolo.wordstack.presentation.navigation.ActivityNavigator;
import com.orm.SugarContext;
import com.squareup.okhttp.OkHttpClient;

import lombok.Getter;

public class WordStack extends Application {

    @Getter private static WordStack instance = new WordStack();
    @Getter private UserSettingsComponentsFactory userSettingsComponentsFactory;
    @Getter private LanguageComponentsFactory languageComponentsFactory;
    @Getter private DrawableComponentsFactory drawableComponentsFactory;
    @Getter private ActivityNavigator activityNavigator;
    private TypefaceManager typefaceManager;
    private TypefaceCollection typefaceCollection;
    private CardsService cardsService;
    private OkHttpClient client;

    public WordStack() {
        instance = this;
        initFields();
    }

    void initFields() {
        userSettingsComponentsFactory = new UserSettingsComponentsFactoryImpl();
        drawableComponentsFactory = new DrawableComponentsFactoryImpl();
        languageComponentsFactory = new LanguageComponentsFactoryImpl();
        activityNavigator = new ActivityNavigator();
        client = new OkHttpClient();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
        typefaceManager = new TypefaceManager();
        typefaceCollection = new TypefaceCollection(this);
        cardsService = new CardsService();
        cardsService.flagService = languageComponentsFactory.getFlagService();
        cardsService.languageService = languageComponentsFactory.getLanguageService();
    }

    public void injectDependenciesTo(StackListActivity stackListActivity) {
        stackListActivity.typefaceCollection = this.typefaceCollection;
        stackListActivity.typefaceManager = this.typefaceManager;
        stackListActivity.navigator = activityNavigator;
        stackListActivity.languageService = languageComponentsFactory.getLanguageService();
        stackListActivity.flagService = languageComponentsFactory.getFlagService();
        stackListActivity.settingsService = userSettingsComponentsFactory.getUserSettingsService();
    }


    public void injectDependenciesTo(GreetingActivity greetingActivity) {
        greetingActivity.navigator = this.activityNavigator;
        greetingActivity.typefaceCollection = this.typefaceCollection;
        greetingActivity.typefaceManager = this.typefaceManager;
    }

    public void injectDependenciesTo(LanguagesActivity languagesActivity) {
        languagesActivity.typefaceCollection = this.typefaceCollection;
        languagesActivity.typefaceManager = this.typefaceManager;
        languagesActivity.navigator = activityNavigator;
        languagesActivity.languageService = languageComponentsFactory.getLanguageService();
        languagesActivity.flagService = languageComponentsFactory.getFlagService();
        languagesActivity.settingsService = userSettingsComponentsFactory.getUserSettingsService();
    }

    public void injectDependenciesTo(MainMenuActivity mainMenuActivity) {
        mainMenuActivity.typefaceCollection = this.typefaceCollection;
        mainMenuActivity.typefaceManager = this.typefaceManager;
        mainMenuActivity.navigator = activityNavigator;
        mainMenuActivity.languageService = languageComponentsFactory.getLanguageService();
        mainMenuActivity.flagService = languageComponentsFactory.getFlagService();
        mainMenuActivity.settingsService = userSettingsComponentsFactory.getUserSettingsService();
    }

    public void injectDependenciesTo(SettingsActivity settingsActivity) {
        settingsActivity.typefaceCollection = this.typefaceCollection;
        settingsActivity.typefaceManager = this.typefaceManager;
        settingsActivity.navigator = activityNavigator;
        settingsActivity.languageService = languageComponentsFactory.getLanguageService();
        settingsActivity.flagService = languageComponentsFactory.getFlagService();
        settingsActivity.settingsService = userSettingsComponentsFactory.getUserSettingsService();
    }

    public void injectDependenciesTo(StackAddDialog stackAddDialog) {
        stackAddDialog.typefaceCollection = this.typefaceCollection;
        stackAddDialog.typefaceManager = this.typefaceManager;
        stackAddDialog.settingsService = this.userSettingsComponentsFactory.getUserSettingsService();
    }

    public void injectDependenciesTo(StackAdapter stackAdapter) {
        stackAdapter.typefaceCollection = this.typefaceCollection;
        stackAdapter.typefaceManager = this.typefaceManager;
    }

    public void injectDependenciesTo(StackActionsDialog stackActionsDialog) {
        stackActionsDialog.typefaceCollection = this.typefaceCollection;
        stackActionsDialog.typefaceManager = this.typefaceManager;
    }

    public void injectDependenciesTo(StackConfirmDeleteDialog stackConfirmDeleteDialog) {
        stackConfirmDeleteDialog.typefaceCollection = this.typefaceCollection;
        stackConfirmDeleteDialog.typefaceManager = this.typefaceManager;
    }

    public void injectDependenciesTo(StackEditDialog stackEditDialog) {
        stackEditDialog.typefaceCollection = this.typefaceCollection;
        stackEditDialog.typefaceManager = this.typefaceManager;
    }

    public void injectDependenciesTo(StackAlert stackAlert) {
        stackAlert.typefaceCollection = this.typefaceCollection;
        stackAlert.typefaceManager = this.typefaceManager;
    }

    public void injectDependenciesTo(StackActivity stackActivity) {
        stackActivity.typefaceCollection = this.typefaceCollection;
        stackActivity.typefaceManager = this.typefaceManager;
        stackActivity.navigator = this.activityNavigator;
        stackActivity.cardsService = this.cardsService;
        stackActivity.languageService = languageComponentsFactory.getLanguageService();
        stackActivity.flagService = languageComponentsFactory.getFlagService();
        stackActivity.settingsService = userSettingsComponentsFactory.getUserSettingsService();
    }

    public void injectDependenciesTo(CardAddDialog cardAddDialog) {
        cardAddDialog.typefaceCollection = this.typefaceCollection;
        cardAddDialog.typefaceManager = this.typefaceManager;
        cardAddDialog.languageService = languageComponentsFactory.getLanguageService();
        cardAddDialog.flagService = languageComponentsFactory.getFlagService();
        cardAddDialog.settingsService = userSettingsComponentsFactory.getUserSettingsService();
        cardAddDialog.client = this.client;
    }

    public void injectDependenciesTo(CardEditDialog cardEditDialog) {
        cardEditDialog.typefaceCollection = this.typefaceCollection;
        cardEditDialog.typefaceManager = this.typefaceManager;
        cardEditDialog.client = this.client;
        cardEditDialog.languageService = languageComponentsFactory.getLanguageService();
        cardEditDialog.flagService = languageComponentsFactory.getFlagService();
        cardEditDialog.settingsService = userSettingsComponentsFactory.getUserSettingsService();
    }

    public void injectDependenciesTo(CardAdapter cardAdapter) {
        cardAdapter.typefaceCollection = this.typefaceCollection;
        cardAdapter.typefaceManager = this.typefaceManager;
    }

    public void injectDependenciesTo(CardItemAdapter cardItemAdapter) {
        cardItemAdapter.typefaceCollection = this.typefaceCollection;
        cardItemAdapter.typefaceManager = this.typefaceManager;
    }

    public void injectDependenciesTo(ChoosePracticeStacksActivity choosePracticeStacksActivity) {
        choosePracticeStacksActivity.typefaceCollection = this.typefaceCollection;
        choosePracticeStacksActivity.typefaceManager = this.typefaceManager;
        choosePracticeStacksActivity.navigator = activityNavigator;
        choosePracticeStacksActivity.languageService = languageComponentsFactory.getLanguageService();
        choosePracticeStacksActivity.flagService = languageComponentsFactory.getFlagService();
        choosePracticeStacksActivity.settingsService = userSettingsComponentsFactory.getUserSettingsService();
    }

    public void injectDependenciesTo(PracticeStackItemAdapter practiceStackItemAdapter) {
        practiceStackItemAdapter.typefaceCollection = this.typefaceCollection;
        practiceStackItemAdapter.typefaceManager = this.typefaceManager;
    }

    public void injectDependenciesTo(PracticeActivity practiceActivity) {
        practiceActivity.typefaceCollection = this.typefaceCollection;
        practiceActivity.typefaceManager = this.typefaceManager;
        practiceActivity.navigator = activityNavigator;
        practiceActivity.languageService = languageComponentsFactory.getLanguageService();
        practiceActivity.flagService = languageComponentsFactory.getFlagService();
        practiceActivity.settingsService = userSettingsComponentsFactory.getUserSettingsService();
        practiceActivity.cardsService = this.cardsService;
    }

    public void injectDependenciesTo(PracticeChooseFaceSideDialog practiceChooseFaceSideDialog) {
        practiceChooseFaceSideDialog.typefaceCollection = this.typefaceCollection;
        practiceChooseFaceSideDialog.typefaceManager = this.typefaceManager;
        practiceChooseFaceSideDialog.languageService = languageComponentsFactory.getLanguageService();
        practiceChooseFaceSideDialog.flagService = languageComponentsFactory.getFlagService();
        practiceChooseFaceSideDialog.settingsService = userSettingsComponentsFactory.getUserSettingsService();
    }

    public void injectDependenciesTo(PracticeFinishedDialog practiceFinishedDialog) {
        practiceFinishedDialog.typefaceCollection = this.typefaceCollection;
        practiceFinishedDialog.typefaceManager = this.typefaceManager;
    }
}
