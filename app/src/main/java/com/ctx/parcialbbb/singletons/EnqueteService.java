package com.ctx.parcialbbb.singletons;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ctx.parcialbbb.modelos.Enquete;
import com.ctx.parcialbbb.modelos.Participante;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EnqueteService {

    public static final Pattern PATTERN_PERGUNTA = Pattern.compile("class=\"poll-highlight.*?<a href=\".*?\">(.*?)</a>");
    public static final Pattern PATTERN_ENQUENTE = Pattern.compile("ng-controller=\"PollController\" ng-init='id=\"(.*?)\"");
    public static final Pattern PATTERN_DADOS = Pattern.compile("div class=\"option-wrapper.*?ng-click=\"enableVote\\('(.*?)'.*?<img src=\"(.*?)\".*?class=\"answer-title\".*?</div>(.*?)</span>");

    public static void craw(Context context,  final Response.Listener<Enquete> listener, final Response.ErrorListener errorListener) {

        final RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://tvefamosos.uol.com.br/bbb/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Matcher matcher = null;

                        matcher = PATTERN_PERGUNTA.matcher(response);

                        if (!matcher.find()) {
                            errorListener.onErrorResponse(new VolleyError());
                            return;
                        }
                        final String question = matcher.group(1);

                        matcher = PATTERN_ENQUENTE.matcher(response);
                        if (!matcher.find()) {
                            errorListener.onErrorResponse(new VolleyError());
                            return;
                        }
                        final String enqueteId = matcher.group(1);

                        matcher = PATTERN_DADOS.matcher(response);
                        final Map<Long, Participante> mapParticipantes = new HashMap<>(2);
                        while (matcher.find()) {
                            Participante p = new Participante();
                            p.setId(Long.valueOf(matcher.group(1)));
                            p.setUrlImage(matcher.group(2));
                            p.setName(matcher.group(3));
                            mapParticipantes.put(p.getId(), p);
                        }
                        StringRequest stringRequestEnquete = new StringRequest(Request.Method.GET, "https://enquete.uol.com.br/results?format=jsonp&jsonp=angular.callbacks._0&id=" + enqueteId,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Matcher matcher = null;
                                        response = response.replace("angular.callbacks._0(", "").replace(")", "");
                                        final Enquete enquete = new Gson().fromJson(response, Enquete.class);

                                        if (enquete == null || !"enabled".equalsIgnoreCase(enquete.getStatus())) {
                                            errorListener.onErrorResponse(new VolleyError());
                                            return;
                                        }

                                        enquete.setQuestion(question);
                                        for (Participante p : enquete.getAnswers()) {
                                            Participante entry = mapParticipantes.get(p.getId());
                                            if (entry != null) {
                                                p.setName(entry.getName());
                                                p.setUrlImage(entry.getUrlImage());
                                            }
                                        }
                                        listener.onResponse(enquete);
                                    }
                                }, errorListener
                        );
                        queue.add(stringRequestEnquete);
                    }
                },errorListener
        );
        queue.add(stringRequest);
    }
}
