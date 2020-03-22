using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

/// <summary>
/// Script to handle treasure box
/// </summary>
public class Treasure : MonoBehaviour
{
    /// <summary>
    /// If items have been taken, sets player at center and removes treasure box
    /// </summary>
    void Start()
    {
        if (PlayerPrefs.GetInt("treasure", 1) == 0)
        {
            gameObject.SetActive(false);
            GameObject player = GameObject.Find("Player");
            player.transform.position = gameObject.transform.position;
        }
    }

    /// <summary>
    /// Handles collision of player and treasure
    /// </summary>
    /// <param name="other">Collision 2D object</param>
    void OnCollisionEnter2D(Collision2D other)
    {
        Destroy(other.gameObject);

        if (other.gameObject.CompareTag("Player"))
        {
            PlayerPrefs.SetInt("treasure", 0);
            SceneManager.LoadScene("Item Choice");
        }
    }
}
