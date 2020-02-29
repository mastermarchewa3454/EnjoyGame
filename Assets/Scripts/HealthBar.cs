using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class HealthBar : MonoBehaviour
{
    Transform bar;

    // Start is called before the first frame update
    void Start()
    {
        bar = transform.Find("Bar");
    }

    void SetSize(float sizeNormalized)
    {
        bar.localScale = new Vector2(sizeNormalized, 1f);
    }
}
