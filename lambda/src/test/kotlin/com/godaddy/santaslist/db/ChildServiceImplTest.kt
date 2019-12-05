package com.godaddy.santaslist.db

import com.godaddy.santaslist.model.Child
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

@DisplayName("ChildServiceImpl should")
internal class ChildServiceImplTest {

    companion object {
        private val CHILD1 = Child(1, "name 1", "address 1", 1)
        private val CHILD2 = Child(2, "name 2", "address 2", 2)
    }

    @Mock lateinit var childCache: Cache<Child>
    @Mock lateinit var childDao: ChildDao

    private lateinit var unit: ChildServiceImpl
    private lateinit var childSet: Set<Int>

    @BeforeEach
    internal fun setUp() {
        initMocks(this)
        childSet = HashSet()

        // simulate cache behavior
        doAnswer { invocation ->
            val id = invocation.arguments[0]
            if (id !is Int) throw RuntimeException("Got non-int id argument")
            childSet = childSet.plus(id)
        }.whenever(childCache.put(any(), any()))

        whenever(childCache.get(any()))
            .thenAnswer { invocation ->
                val id = invocation.arguments[0]
                if (id !is Int) throw RuntimeException("Got non-int id argument")

                if (childSet.contains(id)) {
                    when (id) {
                        1 -> CHILD1
                        2 -> CHILD2
                        else -> null
                    }
                } else {
                    null
                }
            }


        unit = ChildServiceImpl(childDao, childCache)
    }

    @DisplayName("put a child")
    @Test
    internal fun putOnce() {
        unit.putChild(CHILD1)

        val inOrder = inOrder(childCache, childDao)
        inOrder.verify(childCache).get(1)
        inOrder.verify(childCache).put(1, CHILD1)
        inOrder.verify(childDao).putChild(CHILD1)
    }

    @DisplayName("skip the DB write if child is not updated")
    @Test
    internal fun putTwice() {
        unit.putChild(CHILD1)

        verify(childCache).get(1)
        verify(childCache).put(1, CHILD1)
        verify(childDao).putChild(CHILD1)

        unit.putChild(CHILD1)

        verify(childCache).get(1)
        verify(childCache, never()).put(1, CHILD1)
        verifyNoMoreInteractions(childDao)
    }
}