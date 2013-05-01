package com.matrunks.shooter.models;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;

import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpParametersUtils;

/**
 * Objeto callback para Gdx.input.getTextInput ().
 * @author pyrosphere3
 *
 */
public class TextInput implements TextInputListener
{
	private int level;
	
	public TextInput (int level)
	{
		this.level = level;
	}
	
	/**
	 * Este método se llama después de que el usuario introduzca su nombre de usuario.
	 */
	@Override
	public void input (String text)
	{
		Gdx.app.log ("Input", "Registrado usuario " + text);

		// Enviar puntuación a servidor.
		// Los parámetros HTTP se envían a través de un map (id, valor).
		HashMap<String, String> parameters = new HashMap<String, String> ();
		parameters.put ("name", text);
		parameters.put ("score", String.valueOf (level));
		
		if (Gdx.app.getType () == ApplicationType.Android)
		{
			parameters.put ("platform", "android");
		} else if (Gdx.app.getType () == ApplicationType.Desktop)
		{
			parameters.put ("platform", "desktop");
		}

		Gdx.app.log ("Puntuacion", "Jugador: " + text + ", puntuación: " + String.valueOf (level) + ", plataforma: " + Gdx.app.getType ());

		// Petición POST a servidor.
		HttpRequest httpPost = new HttpRequest (HttpMethods.POST);
		httpPost.setUrl ("http://agapito.jesusrozano.es/rest.php");
		httpPost.setContent (HttpParametersUtils.convertHttpParameters (parameters));

		Gdx.net.sendHttpRequest (httpPost, new HttpResponseListener () {
			public void handleHttpResponse (HttpResponse httpResponse)
			{
				Gdx.app.log ("HTTP", String.valueOf (httpResponse.getResultAsString ()));
			}

			public void failed (Throwable t)
			{
				Gdx.app.error ("HTTP", "Error en conexión con servidor." + t.getMessage ());
			}
		});
	}

	/**
	 * Se llama cuando el usuario hace clic en "Cancelar" en el diálogo de introducción de nombre.
	 */
	@Override
	public void canceled ()
	{
	}
}