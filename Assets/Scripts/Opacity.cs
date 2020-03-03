using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Opacity : MonoBehaviour
{
    public KeyCode increaseAlpha;
    public KeyCode decreaseAlpha;
    public float alphaLevel = .5f;

    void Update()
    {
        if(Input.GetKeyDown(increaseAlpha))
            alphaLevel += .05f;
        if(Input.GetKeyDown(decreaseAlpha))
            alphaLevel -= .05f;

        GetComponent<SpriteRenderer>().color = new Color (1,1,1,alphaLevel);
    }
}
