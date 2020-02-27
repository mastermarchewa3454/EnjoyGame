using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyMovement : MonoBehaviour
{
    public float speed = 1;
    public float directionChangeInterval = 1;

    public Rigidbody2D rb;

    Vector2 movement;

    void Awake()
    {
        StartCoroutine(NewHeading());
    }

    void FixedUpdate()
    {
        rb.MovePosition(rb.position + movement * speed * Time.fixedDeltaTime);
    }

    IEnumerator NewHeading()
    {
        while (true)
        {
            NewHeadingRoutine();
            yield return new WaitForSeconds(directionChangeInterval);
        }
    }

    void NewHeadingRoutine()
    {
        movement.x = Random.Range(-1, 2);
        movement.y = Random.Range(-1, 2);
    }
}
