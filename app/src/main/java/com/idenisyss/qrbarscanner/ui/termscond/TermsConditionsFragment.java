package com.idenisyss.qrbarscanner.ui.termscond;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idenisyss.qrbarscanner.R;
import com.idenisyss.qrbarscanner.adapters.TermsconditionAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * create an instance of this fragment.
 */
public class TermsConditionsFragment extends Fragment {
    private final List<TermsCondModel> itemList = new ArrayList<>();
    private RecyclerView terms_con_recycler;
    private TermsconditionAdapter termsconditionAdapter;

    private LinearLayoutManager linearLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_terms_conditions, container, false);

         terms_con_recycler = root.findViewById(R.id.terms_con_recycler);

        TermsCondModel tm = new TermsCondModel(" Acceptance of Terms","By downloading or using this QrBarScanner app, these terms will automatically apply to you – you should make sure therefore that you read them carefully before using the app. You’re not allowed to copy or modify the app, any part of the app, or our trademarks in any way");
        TermsCondModel tm1 = new TermsCondModel(" Privacy Policy","Please review our Privacy Policy, which explains how we collect, use, and protect your personal information. Your use of the App constitutes your consent to the terms of our Privacy Policy.\n" +
                "The app does use third-party services that declare their Terms and Conditions.\n" +
                "Link to Terms and Conditions of third-party service providers used by the app\n" +
                "\uF0A7\tGoogle Play Services\n" +
                "You should be aware that there are certain things that Idenisyss Software solutions  will not take responsibility for. Certain functions of the app will require the app to have an active internet connection. The connection can be Wi-Fi or provided by your mobile network provider, but Idenisyss Software solutions Store cannot take responsibility for the app not working at full functionality if you don’t have access to Wi-Fi, and you don’t have any of your data allowance left.\n" +
                "If you’re using the app outside of an area with Wi-Fi, you should remember that the terms of the agreement with your mobile network provider will still apply. In using the app, you’re accepting responsibility for any such charges, including roaming data charges if you use the app outside of your home territory (i.e. region or country) without turning off data roaming.\n" +
                "Idenisyss Software solutions cannot always take responsibility for the way you use the app i.e. You need to make sure that your device stays charged – if it runs out of battery and you can’t turn it on to avail the Service, Idenisyss Software solutions cannot accept responsibility.\n" +
                "With respect to Idenisyss Software solution’s responsibility for your use of the app, when you’re using the app, it’s important to bear in mind that although we endeavour to ensure that it is updated and correct at all times, we do rely on third parties to provide information to us so that we can make it available to you. Idenisyss Software solutions accepts no liability for any loss, direct or indirect, you experience as a result of relying wholly on this functionality of the app.\n" +
                "At some point, we may wish to update the app. The app is currently available on Android – the requirements for the system (and for any additional systems we decide to extend the availability of the app to) may change, and you’ll need to download the updates if you want to keep using the app. Idenisyss Software solutions does not promise that it will always update the app so that it is relevant to you and/or works with the Android version that you have installed on your device\n");
        TermsCondModel tm2 = new TermsCondModel(" User Responsibilities","The QrBarScanner app stores and processes personal data that you have provided to us, to provide my Service. It’s your responsibility to keep your phone and access to the app secure. We therefore recommend that you do not jailbreak or root your phone, which is the process of removing software restrictions and limitations imposed by the official operating system of your device. It could make your phone vulnerable to malware/viruses/malicious programs, compromise your phone’s security features and it could mean that the QrBarScanner app won’t work properly or at all.");
        TermsCondModel tm3 = new TermsCondModel("Intellectual Property","You’re not allowed to attempt to extract the source code of the app, and you also shouldn’t try to translate the app into other languages or make derivative versions. The app itself, and all the trademarks, copyright, database rights, and other intellectual property rights related to it, still belong to Idenisyss Software solutions.\n" +
                "Idenisyss Software solutions is committed to ensuring that the app is as useful and efficient as possible. For that reason, we reserve the right to make changes to the app at any time and for any reason.\n");
        TermsCondModel tm4 = new TermsCondModel("Termination","We reserve the right to terminate or suspend your access to the App for violations of these Terms and Conditions. Termination may result in the loss of data associated with your account.\n" +
                "\n" +
                "However, you promise to always accept updates to the application when offered to you, We may also wish to stop providing the app, and may terminate use of it at any time without giving notice of termination to you. Unless we tell you otherwise, upon any termination, (a) the rights and licenses granted to you in these terms will end; (b) you must stop using the app, and (if needed) delete it from your device.\n");
        TermsCondModel tm5 = new TermsCondModel("Limitations of Liability","We are not liable for any damages or losses arising from your use of the App. The App is provided \"as is\" without warranties of any kind.\n");
        TermsCondModel tm6 = new TermsCondModel("Dispute Resolution","Any disputes related to the App or these Terms and Conditions will be resolved through arbitration in accordance with the rules of the American Arbitration Association.");
        TermsCondModel tm7 = new TermsCondModel("Updates and Changes","We may update our Terms and Conditions from time to time. Thus, you are advised to review this page periodically for any changes. I will notify you of any changes by posting the new Terms and Conditions on this page.");
        TermsCondModel tm8 = new TermsCondModel("Contact Information","If you have any questions or suggestions about my Terms and Conditions, do not hesitate to contact me at idenisyss.software@gmail.com.\n");
        TermsCondModel tm9 = new TermsCondModel("Last Updated Date :","These terms and conditions are effective as of 2023-09-12.\n");


        itemList.add(tm);
        itemList.add(tm1);
        itemList.add(tm2);
        itemList.add(tm3);
        itemList.add(tm4);
        itemList.add(tm5);
        itemList.add(tm6);
        itemList.add(tm7);
        itemList.add(tm8);
        itemList.add(tm9);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        termsconditionAdapter = new TermsconditionAdapter(getActivity(),itemList);
        terms_con_recycler.setLayoutManager(linearLayoutManager);
        terms_con_recycler.setAdapter(termsconditionAdapter);

        return root;
    }
}