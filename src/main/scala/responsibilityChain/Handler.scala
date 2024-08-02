package responsibilityChain

/**
 * One "node" in the chain of responsibility
 * @tparam T The type of the item to handle
 */
trait Handler[T] {

  /**
   * Handles an item
   * @param item The item to handle
   * @return Updated item after handling
   */
  def handle(item: T): Option[T]

  /**
   * Sets a next handler in the chain
   * @param nextHandler The next handler
   * @return The next handler
   */
  def setNext(nextHandler: Handler[T]): Handler[T]
}
