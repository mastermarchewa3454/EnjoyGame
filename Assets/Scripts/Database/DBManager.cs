﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;
using System.Text;

/// <summary>
/// Base class for handling access to database
/// </summary>
public class DBManager : MonoBehaviour
{
    private string url = "http://18.185.65.201:8080/mathero";
    protected string userId;
    protected string authHeader;

    /// <summary>
    /// Set up userId and authHeader
    /// </summary>
    void Awake()
    {
        userId = PlayerPrefs.GetString("userId", null);
        authHeader = PlayerPrefs.GetString("authHeader", null);
    }

    /// <summary>
    /// Sends a GET request to DB
    /// </summary>
    /// <param name="endpoint">Endpoint to send to</param>
    /// <param name="callback">Callback</param>
    /// <returns></returns>
    protected IEnumerator GetData(string endpoint, System.Action<string> callback = null)
    {
        if (authHeader == null)
            Debug.Log("Log in first");
        else
        {
            UnityWebRequest www = UnityWebRequest.Get(url + endpoint);
            www.SetRequestHeader("Authorization", authHeader);
            yield return www.SendWebRequest();

            if (www.isNetworkError || www.isHttpError)
            {
                Debug.Log(www.error);
                Debug.Log("Error at endpoint " + endpoint);
            }
            else
            {
                Debug.Log(www.responseCode);
                Debug.Log(www.downloadHandler.text);
                if (callback != null)
                    callback(www.downloadHandler.text);
            }
        }
    }

    /// <summary>
    /// Sends a POST request to DB
    /// </summary>
    /// <param name="endpoint">Endpoint to send to</param>
    /// <param name="body">Body of data</param>
    /// <param name="setAuth">Flag to set authHeader, only to be used in log in</param>
    /// <param name="callback">Callback</param>
    /// <returns></returns>
    protected IEnumerator PostData(string endpoint, string body, bool setAuth = false, System.Action<string> callback = null)
    {
        byte[] bodyRaw = Encoding.UTF8.GetBytes(body);
        var www = new UnityWebRequest(url + endpoint, "POST");
        www.uploadHandler = (UploadHandler)new UploadHandlerRaw(bodyRaw);
        www.downloadHandler = (DownloadHandler)new DownloadHandlerBuffer();
        www.SetRequestHeader("Content-Type", "application/json");
        if (authHeader != null)
            www.SetRequestHeader("Authorization", authHeader);

        yield return www.Send();

        if (www.isNetworkError)
        {
            Debug.Log(www.error);
        }
        else
        {
            Debug.Log(www.responseCode);
            if (setAuth)
            {
                if (www.responseCode == 200)
                {
                    foreach (KeyValuePair<string, string> dict in www.GetResponseHeaders())
                    {
                        if (dict.Key == "Authorization")
                        {
                            authHeader = dict.Value;
                            PlayerPrefs.SetString("authHeader", authHeader);
                        }
                        else if (dict.Key == "UserID")
                        {
                            userId = dict.Value;
                            PlayerPrefs.SetString("userId", userId);
                        }
                        else if (dict.Key == "UserRole")
                        {
                            if (dict.Value == "ROLE_TEACHER")
                                PlayerPrefs.SetInt("teacher", 1);
                            else
                                PlayerPrefs.SetInt("teacher", 0);
                        }
                    }
                }
                callback(www.responseCode.ToString());
            }
            else if (callback != null)
            {
                callback(www.downloadHandler.text);
            }
            
        }
    }

}


/// <summary>
/// Helper class to parse JSON arrays
/// </summary>
public static class JsonHelper
{
    public static T[] FromJson<T>(string json)
    {
        Wrapper<T> wrapper = JsonUtility.FromJson<Wrapper<T>>(json);
        Debug.Log(wrapper.wrapperList);
        return wrapper.wrapperList;
    }

    public static string ToJson<T>(T[] array)
    {
        Wrapper<T> wrapper = new Wrapper<T>();
        wrapper.wrapperList = array;
        return JsonUtility.ToJson(wrapper);
    }

    [System.Serializable]
    private class Wrapper<T>
    {
        public T[] wrapperList;
    }
}