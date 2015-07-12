package uit.linh.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import contentprovider.SmsContentProvider;
import models.MessageModel;
import uit.linh.adapters.MessageAdapter;
import uit.linh.suppersms.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EnglishFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EnglishFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnglishFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "category";
    private static final String ARG_PARAM2 = "type";

    // TODO: Rename and change types of parameters
    private int cat;
    private int type;
    private OnFragmentInteractionListener mListener;

    ListView lvMessages;
    ArrayList<MessageModel> arrSms;
    MessageAdapter smsAdapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param category Parameter 1.
     * @param type Parameter 2.
     * @return A new instance of fragment EnglishFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EnglishFragment newInstance(int category, int type) {
        EnglishFragment fragment = new EnglishFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, category);
        args.putInt(ARG_PARAM2, type);
        fragment.setArguments(args);
        return fragment;
    }

    public EnglishFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cat = getArguments().getInt(ARG_PARAM1);
            type = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_english, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lvMessages = (ListView) getActivity().findViewById(R.id.lv_e_messages);
        arrSms = mListener.getMessages(cat, type);
        if (arrSms == null){
            arrSms = new ArrayList<>();
        }
        smsAdapter = new MessageAdapter(getActivity(), R.layout.sms_list_item, arrSms);
        lvMessages.setAdapter(smsAdapter);

        lvMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.sendMessage(arrSms.get(position).getContent());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("on start", "english");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("on resume", "english");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
            // TODO: Update argument type and name
            public void sendMessage(String content);
        public ArrayList<MessageModel> getMessages(int cat, int type);

    }
}
