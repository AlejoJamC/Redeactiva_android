package co.foxdigitalst.redeactiva;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

public class FragmentHistorias extends Fragment {

	public static final String ARG_SECTION_NUMBER = "section_number";

	List<Historia> historias;
	DeportistasListAdapter listaHistoriasAdapter;
	ListView lv_historias;
	public FragmentHistorias() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_historias,
				container, false);
		
		lv_historias = (ListView) rootView
				.findViewById(R.id.lv_historias);
		
		historias = new ArrayList<Historia>();
		
		final ProgressBar progressBar = new ProgressBar(rootView.getContext(), null,
                android.R.attr.progressBarStyle);
        LinearLayout.LayoutParams progressBarParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        progressBar.setLayoutParams(progressBarParams);
        progressBar.setPadding(6, 6, 6, 6);
        progressBar.setVisibility(View.INVISIBLE);

        LinearLayout footerLinearLayout = new LinearLayout(rootView.getContext());
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        footerLinearLayout.setGravity(Gravity.CENTER);
        footerLinearLayout.setLayoutParams(layoutParams);
        footerLinearLayout.addView(progressBar);
		
		lv_historias.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
            	new ObtenerMasHistorias(progressBar,rootView,listaHistoriasAdapter).execute(page);
            }
        });
		
		lv_historias.addFooterView(footerLinearLayout);
		
		listaHistoriasAdapter = new DeportistasListAdapter(getActivity(), historias); 
		 
        // setting list adapter
		lv_historias.setAdapter(listaHistoriasAdapter);
        
		new ObtenerMasHistorias(progressBar,rootView,listaHistoriasAdapter).execute(1);
		
		return rootView;
	}
	
}
