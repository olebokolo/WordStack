package com.olebokolo.wordstack.presentation.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.olebokolo.wordstack.R;
import com.olebokolo.wordstack.core.app.WordStack;
import com.olebokolo.wordstack.core.events.CardAddedEvent;
import com.olebokolo.wordstack.core.languages.flags.FlagService;
import com.olebokolo.wordstack.core.languages.services.LanguageService;
import com.olebokolo.wordstack.core.model.Card;
import com.olebokolo.wordstack.core.model.Language;
import com.olebokolo.wordstack.core.model.Side;
import com.olebokolo.wordstack.core.model.Stack;
import com.olebokolo.wordstack.core.model.UserSettings;
import com.olebokolo.wordstack.core.user.settings.services.UserSettingsService;
import com.olebokolo.wordstack.core.utils.TypefaceCollection;
import com.olebokolo.wordstack.core.utils.TypefaceManager;

import org.greenrobot.eventbus.EventBus;

public class CardAddDialog extends Dialog {

    public TypefaceCollection typefaceCollection;
    public TypefaceManager typefaceManager;
    public LanguageService languageService;
    public FlagService flagService;
    public UserSettingsService settingsService;

    private ImageView frontLangIcon;
    private ImageView backLangIcon;
    private EditText frontLangText;
    private EditText backLangText;

    private Stack stack;
    private Long frontLangId;
    private Long backLangId;

    public CardAddDialog(Activity activity, Stack stack) {
        super(activity);
        this.stack = stack;
        WordStack.getInstance().injectDependenciesTo(this);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setContentView(R.layout.dialog_card_edit);
        findViews();
        setupLanguages();
        setupLanguagesIcons();
        setupFonts();
        setupCloseButton();
        setupBackButton();
        setupAddCardButton();
    }

    private void setupAddCardButton() {
        findViewById(R.id.save_card_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCardFromEnteredData().save();
                EventBus.getDefault().post(new CardAddedEvent());
                hideKeyboardAndDismiss();
            }
        });
    }

    private Card getCardFromEnteredData() {
        String frontWord = frontLangText.getText().toString();
        String backWord = backLangText.getText().toString();
        Long frontSideId = saveCardSide(frontWord, frontLangId);
        Long backSideId = saveCardSide(backWord, backLangId);
        return Card.builder()
                .stackId(stack.getId())
                .frontSideId(frontSideId)
                .backSideId(backSideId)
                .build();
    }

    private long saveCardSide(String text, Long langId) {
        return Side.builder()
                .languageId(langId)
                .content(text)
                .build().save();
    }

    private void findViews() {
        frontLangIcon = (ImageView) findViewById(R.id.front_lang_icon);
        backLangIcon = (ImageView) findViewById(R.id.back_lang_icon);
        frontLangText = (EditText) findViewById(R.id.front_lang_text);
        backLangText = (EditText) findViewById(R.id.back_lang_text);
    }

    private void setupLanguagesIcons() {
        Language frontLanguage = languageService.findById(frontLangId);
        Language backLanguage = languageService.findById(backLangId);
        frontLangIcon.setImageResource(flagService.getFlagByLanguageShortName(frontLanguage.getShortName()));
        backLangIcon.setImageResource(flagService.getFlagByLanguageShortName(backLanguage.getShortName()));
    }

    private void setupLanguages() {
        UserSettings userSettings = settingsService.getUserSettings();
        frontLangId = userSettings.getFrontLangId();
        backLangId = userSettings.getBackLangId();
    }

    private void setupFonts() {
        typefaceManager.setTypefaceForContainer((ViewGroup) findViewById(R.id.root_layout), typefaceCollection.getRalewayMedium());
    }

    private void setupCloseButton() {
        findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardAndDismiss();
            }
        });
    }

    private void setupBackButton() {
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardAndDismiss();
            }
        });
    }

    private void hideKeyboardAndDismiss() {
        hideKeyboard();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 200);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.root_layout).getWindowToken(), 0);
    }

}