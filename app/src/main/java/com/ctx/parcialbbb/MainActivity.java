package com.ctx.parcialbbb;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ctx.parcialbbb.modelos.Enquete;
import com.ctx.parcialbbb.modelos.Participante;
import com.ctx.parcialbbb.singletons.EnqueteService;
import com.ctx.parcialbbb.singletons.ParticipanteContent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements ParticipanteFragment.OnListFragmentInteractionListener {

    public static final String TAG = "MainActivity";

    private TextView mTxtTitulo;
    private MenuItem mMenuAtualizar;
    private Drawable mIconMenuAtualizar;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTxtTitulo = findViewById(R.id.txtTitulo);
        MobileAds.initialize(this, "ca-app-pub-1068712102629927~3227789337");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        buscarInformacao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        mMenuAtualizar = menu.getItem(0);
        mIconMenuAtualizar = mMenuAtualizar.getIcon();
        mIconMenuAtualizar.mutate();
        ativarMenuAtualizar();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAtualizar: {
                buscarInformacao();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void buscarInformacao() {
        desativarMenuAtualizar();
        mTxtTitulo.setText(R.string.msg_Carregando);
        EnqueteService.craw(getApplicationContext(),
                new Response.Listener<Enquete>() {
                    @Override
                    public void onResponse(Enquete enquete) {
                        ParticipanteContent.clear();
                        mTxtTitulo.setText(enquete.getQuestion());
                        ParticipanteContent.addAll(enquete.getAnswers());
                        ParticipanteContent.notifyDataSetChanged();
                        ativarMenuAtualizar();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ParticipanteContent.clear();
                        mTxtTitulo.setText(R.string.msg_Nenhuma_votacao_aberta);
                        if (error != null && error.getMessage() != null) Log.e(TAG, error.getMessage());
                        ativarMenuAtualizar();
                    }
                });
    }

    @Override
    public void onListFragmentInteraction(Participante item) {}

    public void ativarMenuAtualizar() {
        if (mIconMenuAtualizar != null) mIconMenuAtualizar.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP);
        if (mMenuAtualizar != null) mMenuAtualizar.setEnabled(true);
    }

    public void desativarMenuAtualizar() {
        if (mIconMenuAtualizar != null) mIconMenuAtualizar.setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        if (mMenuAtualizar != null) mMenuAtualizar.setEnabled(false);
    }
}
