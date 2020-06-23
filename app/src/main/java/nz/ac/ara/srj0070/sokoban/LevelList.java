package nz.ac.ara.srj0070.sokoban;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import nz.ac.ara.srj0070.model.interfaces.IGame;

public class LevelList extends Fragment {

    public LevelList() {
    }

    public static LevelList newInstance(IGame game) {
        LevelList fragment = new LevelList();
        Bundle args = new Bundle();
        args.putSerializable("game", game);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_level_list, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), (IGame) getArguments().getSerializable("game"), getActivity().getSupportFragmentManager());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }
}
